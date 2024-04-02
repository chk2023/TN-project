package com._3dhs.tnproject.search.controller;

import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.service.PostService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexableFieldType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SearchController {

    private final PostService postService;

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
