CREATE TABLE IF NOT EXISTS `tbl_attachment`
(
    `file_code`    INT NOT NULL AUTO_INCREMENT
        COMMENT '파일번호',
    `origin_name`    VARCHAR(50) NOT NULL COMMENT '원래이름',
    `safe_name`    VARCHAR(50) NOT NULL COMMENT '안전한이름',
    `file_path`    VARCHAR(50) NOT NULL COMMENT '경로',
    `post_code`    INT NOT NULL COMMENT '글번호',
    PRIMARY KEY ( `file_code` )
) COMMENT = '첨부파일';


CREATE TABLE IF NOT EXISTS `tbl_block`
(
    `member_code`    INT NOT NULL COMMENT '회원번호',
    `block_member_code`    INT NOT NULL COMMENT '차단한회원번호',
    PRIMARY KEY ( `member_code`,`block_member_code` )
) COMMENT = '차단목록';

# ALTER TABLE `tbl_block`
#     ADD CONSTRAINT `tbl_block_PK` PRIMARY KEY ( `member_code`,`block_member_code` );


CREATE TABLE IF NOT EXISTS `tbl_comments`
(
    `cmt_code`    INT NOT NULL AUTO_INCREMENT
        COMMENT '댓글번호',
    `post_code`    INT NOT NULL COMMENT '글번호',
    `cmt_text`    VARCHAR(50) NOT NULL COMMENT '댓글내용',
    `cmt_wri_date`    DATETIME NOT NULL COMMENT '댓글작성일',
    `member_code`    INT NOT NULL COMMENT '댓글작성자번호',
    `parents_code`    INT COMMENT '부모댓글번호',
    `cmt_mod_date`    DATETIME COMMENT '댓글수정시간',
    `cmt_dele_date`    DATETIME COMMENT '댓글삭제시간',
    `cmt_is_deleted`    BOOLEAN NOT NULL DEFAULT false COMMENT '삭제여부',
    PRIMARY KEY ( `cmt_code` )
) COMMENT = '댓글';


CREATE TABLE IF NOT EXISTS `tbl_folder`
(
    `folder_code`    INT NOT NULL AUTO_INCREMENT
        COMMENT '폴더번호',
    `folder_name`    VARCHAR(20) NOT NULL COMMENT '폴더이름',
    `folder_icon_path`    VARCHAR(50) DEFAULT 'default_icon_path' NOT NULL COMMENT '폴더아이콘',
    `folder_sequence`    INT NOT NULL COMMENT '폴더순서',
    PRIMARY KEY ( `folder_code` )
) COMMENT = '폴더';


CREATE TABLE IF NOT EXISTS `tbl_interest`
(
    `member_code`    INT NOT NULL COMMENT '회원번호',
    `tag_code`    INT NOT NULL COMMENT '태그번호',
    `interest`    INT NOT NULL COMMENT '관심도',
    PRIMARY KEY ( `member_code`,`tag_code` )
) COMMENT = '회원관심도';

# ALTER TABLE `tbl_interest`
#     ADD CONSTRAINT `tbl_interest_PK` PRIMARY KEY ( `member_code`,`tag_code` );


CREATE TABLE IF NOT EXISTS `tbl_like_list`
(
    `post_code`    INT NOT NULL COMMENT '글번호',
    `member_code`    INT NOT NULL COMMENT '회원번호',
    `is_private`    BOOLEAN NOT NULL DEFAULT false COMMENT '비공개여부',
    PRIMARY KEY ( `member_code`,`post_code` )
) COMMENT = '좋아요한목록';

# ALTER TABLE `tbl_like_list`
#     ADD CONSTRAINT `tbl_like_list_PK` PRIMARY KEY ( `member_code`,`post_code` );


CREATE TABLE IF NOT EXISTS `tbl_manager`
(
    `manager_code`    INT NOT NULL AUTO_INCREMENT
        COMMENT '관리자번호',
    `manager_name`    VARCHAR(20) NOT NULL COMMENT '관리자이름',
    `member_code`    INT NOT NULL UNIQUE KEY
        COMMENT '회원번호',
    PRIMARY KEY ( `manager_code` )
) COMMENT = '관리자목록';


CREATE TABLE IF NOT EXISTS `tbl_member`
(
    `member_code`    INT NOT NULL AUTO_INCREMENT
        COMMENT '회원번호',
    `member_id`    VARCHAR(20) NOT NULL COMMENT '회원ID',
    `member_pwd`    VARCHAR(20) NOT NULL COMMENT '회원PWD',
    `member_gender`    VARCHAR(2) DEFAULT 'M' COMMENT '회원성별',
    `member_age`    INT COMMENT '회원나이',
    `member_birth`    DATETIME COMMENT '회원생일',
    `member_subdate`    DATETIME NOT NULL COMMENT '회원가입일',
    `member_status`    VARCHAR(10) NOT NULL COMMENT '회원상태',
    `have_tissue`    INT NOT NULL COMMENT '보유티슈',
    `member_authority`    VARCHAR(10) NOT NULL COMMENT '회원권한',
    `dormant_trans_date`    DATETIME COMMENT '휴면전환일',
    `suspend_trans_date`    DATETIME COMMENT '정지전환일',
    PRIMARY KEY ( `member_code` )
) COMMENT = '회원';


CREATE TABLE IF NOT EXISTS `tbl_post`
(
    `post_code`    INT NOT NULL AUTO_INCREMENT
        COMMENT '글번호',
    `post_title`    VARCHAR(50) NOT NULL COMMENT '글제목',
    `post_text`    VARCHAR(1000) NOT NULL COMMENT '글내용',
    `post_status`    VARCHAR(10) NOT NULL COMMENT '글상태',
    `post_price`    INT NOT NULL COMMENT '글설정티슈',
    `post_wri_date`    DATETIME NOT NULL COMMENT '글작성시간',
    `post_view`    INT NOT NULL COMMENT '글조회수',
    `member_code`    INT NOT NULL COMMENT '글작성자번호',
    `post_mod_date`    DATETIME COMMENT '글수정시간',
    `post_dele_date`    DATETIME COMMENT '글삭제시간',
    `post_is_deleted`    BOOLEAN NOT NULL COMMENT '삭제여부',
    `folder_code`    INT NOT NULL COMMENT '폴더번호',
    PRIMARY KEY ( `post_code` )
) COMMENT = '글';


CREATE TABLE IF NOT EXISTS `tbl_post-tag`
(
    `post_code`    INT NOT NULL COMMENT '글번호',
    `tag_code`    INT NOT NULL COMMENT '태그번호',
    PRIMARY KEY ( `post_code`,`tag_code` )
) COMMENT = '글-태그';

# ALTER TABLE `tbl_post-tag`
#     ADD CONSTRAINT `tbl_post-tag_PK` PRIMARY KEY ( `post_code`,`tag_code` );


CREATE TABLE IF NOT EXISTS `tbl_profile`
(
    `member_code`    INT NOT NULL COMMENT '회원번호',
    `profile_code`    INT NOT NULL COMMENT '프로필번호',
    `profile_photo_path`    VARCHAR(50) DEFAULT 'default_photoPath' NOT NULL COMMENT '프로필사진',
    `profile_nickname`    VARCHAR(20) NOT NULL COMMENT '프로필닉네임',
    `profile_statmsg`    VARCHAR(50) COMMENT '프로필상태메세지',
    `profile_bg_path`    VARCHAR(50) DEFAULT 'default_bg_path' NOT NULL COMMENT '블로그배경사진',
    PRIMARY KEY ( `profile_code`,`member_code`,`profile_photo_path` )
) COMMENT = '프로필';

# ALTER TABLE `tbl_profile`
#     ADD CONSTRAINT `tbl_profile_PK` PRIMARY KEY ( `profile_code`,`member_code`,`profile_photo_path` );


CREATE TABLE IF NOT EXISTS `tbl_r_category`
(
    `r_category_code`    INT NOT NULL AUTO_INCREMENT
        COMMENT '신고구분코드',
    `r_category_name`    VARCHAR(20) COMMENT '신고구분이름',
    PRIMARY KEY ( `r_category_code` )
) COMMENT = '신고구분';


CREATE TABLE IF NOT EXISTS `tbl_report`
(
    `report_code`    INT NOT NULL AUTO_INCREMENT
        COMMENT '신고번호',
    `r_category_code`    INT NOT NULL COMMENT '신고구분코드',
    `report_text`    VARCHAR(50) COMMENT '신고내용',
    `report_date`    DATETIME NOT NULL COMMENT '신고일자',
    `report_status`    VARCHAR(2) COMMENT '처리상태',
    `processing_date`    DATETIME COMMENT '처리날짜',
    `processing_text`    VARCHAR(50) COMMENT '처리내용',
    `sub_member_code`    INT NOT NULL COMMENT '대상유저번호',
    `manager_code`    INT COMMENT '관리자번호',
    `reporter_code`    INT NOT NULL COMMENT '신고자번호',
    `report_content`    VARCHAR(2) NOT NULL COMMENT '신고콘텐츠',
    `post_code`    INT COMMENT '글번호',
    `cmt_code`    INT COMMENT '댓글번호',
    PRIMARY KEY ( `report_code` )
) COMMENT = '신고';


CREATE TABLE IF NOT EXISTS `tbl_tag`
(
    `tag_code`    INT NOT NULL AUTO_INCREMENT
        COMMENT '태그번호',
    `tag_name`    VARCHAR(20) NOT NULL COMMENT '태그이름',
    PRIMARY KEY ( `tag_code` )
) COMMENT = '태그';


CREATE TABLE IF NOT EXISTS `tbl_tissue`
(
    `order_code`    INT NOT NULL AUTO_INCREMENT COMMENT '주문번호',
    `order_class`    VARCHAR(10) NOT NULL COMMENT '상품구분',
    `order_date`    DATETIME NOT NULL COMMENT '일자',
    `tissue_price`    INT NOT NULL COMMENT '금액',
    `member_code`    INT NOT NULL COMMENT '회원번호',
    `post_code`    INT COMMENT '글번호',
    PRIMARY KEY ( `order_code` )
) COMMENT = '티슈';

# ALTER TABLE `tbl_tissue`
#     ADD CONSTRAINT `tbl_tissue_PK` PRIMARY KEY ( `order_code` );


