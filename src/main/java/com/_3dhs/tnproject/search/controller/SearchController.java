package com._3dhs.tnproject.search.controller;

import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.service.PostService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SearchController {

    private final PostService postService;
    private final MessageSourceAccessor accessor;

    public static final String INDEX_PATH;

    static {
        try {
            INDEX_PATH = ResourceUtils.getFile("classpath:static/index").getAbsolutePath();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Document> getAllDoc() {
        //Doc파일을 만들기 위해서 Post정보를 가져오기
        List<PostDTO> postList = postService.findAllPostListForDoc();
        log.info("getAllDoc 실행... DB에서 불러온 정보의 수 : {}",postList.size());
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
            tempDoc.add(new Field("postCode",String.valueOf(postList.get(i).getPostCode()),fieldType));
            tempDoc.add(new Field("postTitle",String.valueOf(postList.get(i).getPostTitle()),fieldType));
            tempDoc.add(new Field("postText",String.valueOf(postList.get(i).getPostText()),fieldType));
            tempDoc.add(new Field("memberCode",String.valueOf(postList.get(i).getMemberCode()),fieldType));
            resultList.add(tempDoc);
        }

        return resultList;
    }

    @PostMapping("/search/result")
    public String search(String searchValue, Model model, RedirectAttributes attributes, HttpServletRequest request) {
        List<Integer> postCodes = new ArrayList<>();
        try (IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX_PATH)))){
            IndexSearcher searcher = new IndexSearcher(reader);
            StandardAnalyzer analyzer = new StandardAnalyzer();
            //QueryBuilder queryBuilder = new QueryBuilder(analyzer);

            Query query = new WildcardQuery(new Term("postText", "*" + searchValue + "*"));

            TopDocs result = searcher.search(query, 10);
            ScoreDoc[] hits = result.scoreDocs;
            for (ScoreDoc hit : hits) {
                postCodes.add(Integer.parseInt(searcher.doc(hit.doc).get("postCode")));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!postCodes.isEmpty()) {
            List<PostDTO> postList = postService.findListByPostCodes(postCodes);
            model.addAttribute("postList", postList);
            return "/common/search_result"; //TODO : 테스트용 화면으로 연결중 수정바람
        } else {
            attributes.addFlashAttribute("message", accessor.getMessage("search.noResult"));
            String tempURL = request.getHeader("Referer");
            return "redirect:" + tempURL;
        }


    }
    @PostConstruct
    public void indexInitializer() {
        log.warn("index 삭제 실행");
        File indexDir = new File(INDEX_PATH);
        if (indexDir.exists() && indexDir.isDirectory()) {
            File[] files = indexDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    log.warn("{}삭제됨...",file);
                    file.delete();
                }
            }
        }
    }
}
