package com._3dhs.tnproject.search.controller;

import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.service.PostService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@Controller
@Slf4j
public class SearchController {

    private final PostService postService;
    private final MessageSourceAccessor accessor;

    public static final String INDEX_PATH;
    public static final IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());

    static {
        try {
            INDEX_PATH = ResourceUtils.getFile("classpath:static/index").getAbsolutePath();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public SearchController(PostService postService, MessageSourceAccessor accessor) {
        this.postService = postService;
        this.accessor = accessor;
    }

    public List<Document> getAllDoc() {
        //Doc파일을 만들기 위해서 Post정보를 가져오기
        List<PostDTO> postList = postService.findAllPostListForDoc();
        log.info("getAllDoc 실행... DB에서 불러온 정보의 수 : {}", postList.size());
        //필요한 데이터만 가공해서 DOC에 저장후 반환
        return postDTOListToDocList(postList);
    }

    private List<Document> postDTOListToDocList(List<PostDTO> postList) {
        List<Document> resultList = new ArrayList<>();
        FieldType fieldType = new FieldType();
        fieldType.setStored(true); // 필드의 값을 저장할지 여부
        fieldType.setTokenized(true); // 필드의 값을 토큰화할지 여부
        fieldType.setIndexOptions(IndexOptions.DOCS); // 색인에 대한 옵션 설정
        for (int i = 0; i < postList.size(); i++) {
            Document tempDoc = new Document();
            tempDoc.add(new Field("postCode", String.valueOf(postList.get(i).getPostCode()), fieldType));
            tempDoc.add(new Field("postTitle", String.valueOf(postList.get(i).getPostTitle()), fieldType));
            tempDoc.add(new Field("postText", String.valueOf(postList.get(i).getPostText()), fieldType));
            tempDoc.add(new Field("memberCode", String.valueOf(postList.get(i).getMemberCode()), fieldType));
            resultList.add(tempDoc);
        }

        return resultList;
    }

    @PostMapping("/search/result")
    public String search(String searchValue,
                         @RequestParam(required = false, defaultValue = "atMain") String searchType,
                         @RequestParam(required = false, defaultValue = "0") int memberCode,
                         Model model,
                         RedirectAttributes attributes,
                         HttpServletRequest request) {
        Set<Integer> postCodes = new HashSet<>(); // 중복값을 자동으로 제거하기 위해서 Set사용
        List<PostDTO> postList;
        log.info("검색시작...");
        log.info("검색타입 : {}",searchType);
        log.info("맴버코드 : {}",memberCode);
        log.info("검색값 : {}", searchValue);
        if (searchValue.isBlank()) {
            log.error("검색값이 입력되지 않았습니다.");
            attributes.addFlashAttribute("message", accessor.getMessage("search.noValue"));
            String tempURL = request.getHeader("Referer");
            return "redirect:" + tempURL;
        }
        try (IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX_PATH)))) {
            IndexSearcher searcher = new IndexSearcher(reader);
            //StandardAnalyzer analyzer = new StandardAnalyzer();
            //QueryBuilder queryBuilder = new QueryBuilder(analyzer);

            Query query1 = new WildcardQuery(new Term("postText", "*" + searchValue + "*"));
            Query query2 = new WildcardQuery(new Term("postTitle", "*" + searchValue + "*"));
            BooleanQuery.Builder booleanQueryBuilder = new BooleanQuery.Builder();
            booleanQueryBuilder.add(query1, BooleanClause.Occur.SHOULD);
            booleanQueryBuilder.add(query2, BooleanClause.Occur.SHOULD); // 해당 조건이 적용된 쿼리는 검색 결과에 포함되어도 되지만, 만족하지 않아도 됨

            Query query = booleanQueryBuilder.build();

            TopDocs result = searcher.search(query, Integer.MAX_VALUE); //해당하는 모든 결과를 반환하도록 설정
            ScoreDoc[] hits = result.scoreDocs;
            for (ScoreDoc hit : hits) {
                postCodes.add(Integer.parseInt(searcher.doc(hit.doc).get("postCode")));
                log.info("서치됨... : {}",searcher.doc(hit.doc).get("postCode"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //검색결과가 있으면 DB에서 값을 가져옴
        if (!postCodes.isEmpty()) {
            postList = postService.findListByPostCodes(postCodes);
        } else {
            log.error("검색결과없음.");
            attributes.addFlashAttribute("message", accessor.getMessage("search.noResult"));
            String tempURL = request.getHeader("Referer");
            return "redirect:" + tempURL;
        }
        //검색이 블로그에서 이루어졌다면 블로그 주인의 맴버코드에 해당하지 않는 글 제거
        if ("atBlog".equals(searchType)) {
            Iterator<PostDTO> iterator = postList.iterator();
            while (iterator.hasNext()) {
                PostDTO post = iterator.next();
                if (post.getMemberCode() != memberCode) {
                    iterator.remove();
                    log.info("제거됨(검색조건에 맞지않음) : {}", post.getPostCode());
                }
            }
        }
        log.info("검색완료");
        model.addAttribute("postList", postList);
        return "/common/search_result"; //TODO : 테스트용 화면으로 연결중 수정바람
    }

    @PostConstruct
    public void indexInitializer() {
        log.warn("index 삭제 실행");
        File indexDir = new File(INDEX_PATH);
        if (indexDir.exists() && indexDir.isDirectory()) {
            File[] files = indexDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    log.warn("{}삭제됨...", file);
                    file.delete();
                }
            }
        }
    }
}
