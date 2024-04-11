CREATE TABLE `tbl_attachment`
(
    `file_code`   INT                            NOT NULL AUTO_INCREMENT
        COMMENT '파일번호',
    `origin_name` VARCHAR(100)                   NOT NULL COMMENT '원래이름',
    `safe_name`   VARCHAR(255)                   NOT NULL COMMENT '안전한이름',
    `file_path`   VARCHAR(50) DEFAULT 'no_image' NOT NULL COMMENT '경로',
    `post_code`   INT                            NOT NULL COMMENT '글번호',
    PRIMARY KEY (`file_code`)
) COMMENT = '첨부파일';

CREATE UNIQUE INDEX `tbl_attachment_PK` ON `tbl_attachment`
    (`file_code`);


CREATE TABLE `tbl_block`
(
    `b_member_code`  INT NOT NULL COMMENT '회원번호',
    `blocked_m_code` INT NOT NULL COMMENT '차단한회원번호',
    PRIMARY KEY (`b_member_code`, `blocked_m_code`)
) COMMENT = '차단목록';

CREATE UNIQUE INDEX `tbl_block_PK` ON `tbl_block`
    (`b_member_code`, `blocked_m_code`);


CREATE TABLE `tbl_comments`
(
    `cmt_code`       INT                   NOT NULL AUTO_INCREMENT
        COMMENT '댓글번호',
    `post_code`      INT                   NOT NULL COMMENT '글번호',
    `cmt_text`       VARCHAR(50)           NOT NULL COMMENT '댓글내용',
    `cmt_wri_date`   DATETIME              NOT NULL COMMENT '댓글작성일',
    `member_code`    INT                   NOT NULL COMMENT '댓글작성자번호',
    `parents_code`   INT COMMENT '부모댓글번호',
    `cmt_mod_date`   DATETIME COMMENT '댓글수정시간',
    `cmt_dele_date`  DATETIME COMMENT '댓글삭제시간',
    `cmt_is_deleted` BOOLEAN DEFAULT False NOT NULL COMMENT '삭제여부',
    PRIMARY KEY (`cmt_code`)
) COMMENT = '댓글';

CREATE UNIQUE INDEX `tbl_comments_PK` ON `tbl_comments`
    (`cmt_code`);


CREATE TABLE `tbl_folder`
(
    `folder_code`      INT                                           NOT NULL AUTO_INCREMENT COMMENT '폴더번호',
    `folder_name`      VARCHAR(20)                                   NOT NULL COMMENT '폴더이름',
    `folder_icon_path` VARCHAR(50) DEFAULT '/images/icon_folder.png' NOT NULL COMMENT '폴더아이콘',
    `folder_sequence`  INT                                           NOT NULL COMMENT '폴더순서',
    `f_member_code`    INT                                           NOT NULL COMMENT '회원번호',
    `folder_status`    VARCHAR(10) DEFAULT 'N'                       NOT NULL COMMENT '폴더상태',
    PRIMARY KEY (`folder_code`)
) COMMENT = '폴더';

CREATE UNIQUE INDEX `tbl_folder_PK` ON `tbl_folder`
    (`folder_code`);


CREATE TABLE `tbl_interest`
(
    `i_member_code` INT NOT NULL COMMENT '회원번호',
    `tag_code`      INT NOT NULL COMMENT '태그번호',
    `interest`      INT NOT NULL COMMENT '관심도',
    PRIMARY KEY (`i_member_code`, `tag_code`)
) COMMENT = '회원관심도';

CREATE UNIQUE INDEX `tbl_interest_PK` ON `tbl_interest`
    (`i_member_code`, `tag_code`);


CREATE TABLE `tbl_like_list`
(
    `post_code`     INT                   NOT NULL COMMENT '글번호',
    `l_member_code` INT                   NOT NULL COMMENT '회원번호',
    `is_private`    BOOLEAN DEFAULT FALSE NOT NULL COMMENT '비공개여부',
    PRIMARY KEY (`l_member_code`, `post_code`)
) COMMENT = '좋아요한목록';

CREATE UNIQUE INDEX `tbl_like_list_PK` ON `tbl_like_list`
    (`l_member_code`, `post_code`);


CREATE TABLE `tbl_manager`
(
    `manager_code`  INT         NOT NULL AUTO_INCREMENT
        COMMENT '관리자번호',
    `manager_name`  VARCHAR(20) NOT NULL COMMENT '관리자이름',
    `m_member_code` INT         NOT NULL COMMENT '회원번호',
    PRIMARY KEY (`manager_code`)
) COMMENT = '관리자목록';

CREATE UNIQUE INDEX `tbl_manager_PK` ON `tbl_manager`
    (`manager_code`);


CREATE TABLE `tbl_member`
(
    `member_code`        INT                          NOT NULL AUTO_INCREMENT
        COMMENT '회원번호',
    `member_id`          VARCHAR(50)                  NOT NULL COMMENT '회원ID',
    `member_pwd`         VARCHAR(255)                 NOT NULL COMMENT '회원PWD',
    `member_gender`      VARCHAR(10) DEFAULT 'MALE' COMMENT '회원성별',
    `member_age`         INT COMMENT '회원나이',
    `member_birth`       DATETIME COMMENT '회원생일',
    `member_subdate`     DATETIME                     NOT NULL DEFAULT (now()) COMMENT '회원가입일',
    `member_status`      VARCHAR(10) DEFAULT 'ACTIVE' NOT NULL COMMENT '회원상태',
    `have_tissue`        INT         DEFAULT 0        NOT NULL COMMENT '보유티슈',
    `member_authority`   VARCHAR(10) DEFAULT 'COMMON' NOT NULL COMMENT '회원권한',
    `dormant_trans_date` DATETIME COMMENT '휴면전환일',
    `suspend_trans_date` DATETIME COMMENT '정지전환일',
    `delete_trans_date`  DATETIME COMMENT '탈퇴전환일',
    `is_deleted`         BOOLEAN     DEFAULT FALSE    NOT NULL COMMENT '탈퇴여부',
    PRIMARY KEY (`member_code`)
) COMMENT = '회원';

CREATE UNIQUE INDEX `tbl_member_PK` ON `tbl_member`
    (`member_code`);


CREATE TABLE `tbl_post`
(
    `post_code`       INT                          NOT NULL AUTO_INCREMENT
        COMMENT '글번호',
    `post_title`      VARCHAR(50)                  NOT NULL COMMENT '글제목',
    `post_text`       VARCHAR(1000)                NOT NULL COMMENT '글내용',
    `post_status`     VARCHAR(10) DEFAULT 'PUBLIC' NOT NULL COMMENT '글상태',
    `post_price`      INT         DEFAULT 0        NOT NULL COMMENT '글설정티슈',
    `post_wri_date`   DATETIME                     NOT NULL COMMENT '글작성시간',
    `post_view`       INT         DEFAULT 0        NOT NULL COMMENT '글조회수',
    `po_member_code`  INT                          NOT NULL COMMENT '글작성자번호',
    `post_mod_date`   DATETIME COMMENT '글수정시간',
    `post_dele_date`  DATETIME COMMENT '글삭제시간',
    `post_is_deleted` BOOLEAN     DEFAULT FALSE    NOT NULL COMMENT '삭제여부',
    `post_is_fixed`   BOOLEAN     DEFAULT FALSE    NOT NULL COMMENT '고정여부',
    `folder_code`     INT         DEFAULT 0        NOT NULL COMMENT '폴더번호',
    PRIMARY KEY (`post_code`)
) COMMENT = '글';

CREATE UNIQUE INDEX `tbl_post_PK` ON `tbl_post`
    (`post_code`);


CREATE TABLE `tbl_post_tag`
(
    `post_code` INT NOT NULL COMMENT '글번호',
    `tag_code`  INT NOT NULL COMMENT '태그번호',
    PRIMARY KEY (`post_code`, `tag_code`)
) COMMENT = '글-태그';

CREATE UNIQUE INDEX `tbl_post_tag_PK` ON `tbl_post_tag`
    (`post_code`, `tag_code`);


CREATE TABLE `tbl_profile`
(
    `pr_member_code`   INT                                                 NOT NULL COMMENT '회원번호',
    `profile_code`     INT                                                 NOT NULL AUTO_INCREMENT
        COMMENT '프로필번호',
    `profile_nickname` VARCHAR(50)                                         NOT NULL COMMENT '프로필닉네임',
    `profile_statmsg`  VARCHAR(50) COMMENT '프로필상태메세지',
    `profile_img_path` VARCHAR(200) DEFAULT '/images/icon_user.png'        NOT NULL COMMENT '프로필사진',
    `profile_bg_path`  VARCHAR(200) DEFAULT '/images/icon_no_image_lg.png' NOT NULL
        COMMENT '블로그배경사진',
    PRIMARY KEY (`profile_code`, `pr_member_code`)
) COMMENT = '프로필';

CREATE UNIQUE INDEX `tbl_profile_PK` ON `tbl_profile`
    (`profile_code`, `pr_member_code`);


CREATE TABLE `tbl_r_category`
(
    `r_category_code` INT NOT NULL AUTO_INCREMENT
        COMMENT '신고구분코드',
    `r_category_name` VARCHAR(20) COMMENT '신고구분이름',
    PRIMARY KEY (`r_category_code`)
) COMMENT = '신고구분';

CREATE UNIQUE INDEX `tbl_r_category_PK` ON `tbl_r_category`
    (`r_category_code`);


CREATE TABLE `tbl_report`
(
    `report_code`     INT         NOT NULL AUTO_INCREMENT
        COMMENT '신고번호',
    `r_category_code` INT         NOT NULL COMMENT '신고구분코드',
    `report_text`     VARCHAR(150) COMMENT '신고내용',
    `report_date`     DATETIME    NOT NULL COMMENT '신고일자',
    `report_status`   VARCHAR(15) COMMENT '처리상태',
    `processing_date` DATETIME COMMENT '처리날짜',
    `processing_text` VARCHAR(150) COMMENT '처리내용',
    `target_code`     INT         NOT NULL COMMENT '대상유저번호',
    `manager_code`    INT COMMENT '관리자번호',
    `reporter_code`   INT         NOT NULL COMMENT '신고자번호',
    `report_content`  VARCHAR(15) NOT NULL COMMENT '신고콘텐츠',
    `cmt_code`        INT COMMENT '댓글번호',
    `post_code`       INT COMMENT '글번호',
    PRIMARY KEY (`report_code`)
) COMMENT = '신고';

CREATE UNIQUE INDEX `tbl_report_PK` ON `tbl_report`
    (`report_code`);


CREATE TABLE `tbl_tag`
(
    `tag_code` INT         NOT NULL AUTO_INCREMENT
        COMMENT '태그번호',
    `tag_name` VARCHAR(20) NOT NULL COMMENT '태그이름',
    PRIMARY KEY (`tag_code`)
) COMMENT = '태그';

CREATE UNIQUE INDEX `tbl_tag_PK` ON `tbl_tag`
    (`tag_code`);


CREATE TABLE `tbl_tissue`
(
    `tissue_code`   INT         NOT NULL AUTO_INCREMENT
        COMMENT '티슈번호',
    `ump_uid`       VARCHAR(50) COMMENT '결제고유번호',
    `marchant_uid`  VARCHAR(50) COMMENT '주문번호',
    `order_class`   VARCHAR(10) NOT NULL COMMENT '상품구분',
    `order_date`    DATETIME    NOT NULL COMMENT '일자',
    `tissue_price`  INT         NOT NULL COMMENT '금액',
    `t_member_code` INT         NOT NULL COMMENT '회원번호',
    `post_code`     INT COMMENT '글번호',
    PRIMARY KEY (`tissue_code`)
) COMMENT = '티슈';

CREATE UNIQUE INDEX `tbl_tissue_PK` ON `tbl_tissue`
    (`tissue_code`);

# 과도한 join때문에 추가
CREATE VIEW post_view AS
SELECT po.post_code,
       po.post_title,
       po.post_text,
       po.post_price,
       po.po_member_code,
       po.folder_code,
       po.post_wri_date,
       po.post_mod_date,
       po.post_status,
       po.post_is_deleted,
       po.post_is_fixed,
       pr.profile_code,
       pr.profile_nickname,
       pr.profile_statmsg,
       pr.profile_img_path,
       IFNULL(li.like_count, 0) AS like_count,
       IFNULL(cm.cmt_count, 0)  AS cmt_count
FROM tbl_post po
         JOIN
     tbl_profile pr ON (po.po_member_code = pr.pr_member_code)
         LEFT JOIN
     (SELECT post_code,
             COUNT(*) AS cmt_count
      FROM tbl_comments
      GROUP BY post_code) cm ON cm.post_code = po.post_code
         LEFT JOIN
     (SELECT post_code,
             COUNT(*) AS like_count
      FROM tbl_like_list
      GROUP BY post_code) li ON li.post_code = po.post_code
ORDER BY po.post_code;

