package com._3dhs.tnproject;

import com._3dhs.tnproject.search.controller.SearchController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static com._3dhs.tnproject.search.controller.SearchController.INDEX_PATH;

@SpringBootApplication
//@ComponentScan("com._3dhs.tnproject")
@Slf4j
public class TnProjectApplication implements CommandLineRunner {
    private final SearchController searchController;

    public TnProjectApplication(SearchController searchController) {
        this.searchController = searchController;
    }

    public static void main(String[] args) {
        SpringApplication.run(TnProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("run 실행됨...");
        // Lucene 인덱스 생성
        Directory indexDir = FSDirectory.open(Paths.get(INDEX_PATH));
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter writer = new IndexWriter(indexDir,config);

        //데이터베이스에서 데이터를 가져와 Lucene 인덱싱
        List<Document> documents = searchController.getAllDoc();

        log.info("documents의 사이즈 : {}",documents.size());
        documents.forEach(doc -> {
            try {
                log.info("doc 추가됨 : {}",doc.get("postTitle"));
                writer.addDocument(doc);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        writer.close();
        log.info("run 실행종료...");
    }
}
