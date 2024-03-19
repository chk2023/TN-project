INSERT INTO tissuenetworkdb.tbl_member
(member_id, member_pwd, member_gender, member_age, member_birth, member_subdate, member_status, have_tissue,
 member_authority)
VALUES ('admin@gmail.com', 'admin', 'M', 20, '2012-12-12 00:00:00', '2024-03-19 00:00:00', 'A', 1000000, 'ADMIN'),
       ('user01@gmail.com', 'pass01', 'F', 18, '2021-01-01 00:00:00', '2024-03-19 00:00:00', 'A', 0, 'ADMIN');


INSERT INTO tbl_tag(tag_name)
VALUES ('강아지'),
       ('고양이'),
       ('커피'),
       ('컴퓨터'),
       ('노트'),
       ('삼성'),
       ('대학교'),
       ('종로'),
       ('강의'),
       ('자바'),
       ('코딩'),
       ('게임'),
       ('모자'),
       ('모니터'),
       ('컴퓨터'),
       ('마우스'),
       ('깃허브'),
       ('개발'),
       ('숲'),
       ('좋아요_주세요');
INSERT INTO tbl_folder(folder_name, folder_icon_path, folder_sequence)
VALUES ('기본값폴더', '/foldericon/default.png', 0);

INSERT INTO tbl_post
(post_title, post_text, post_status, post_price, post_wri_date, post_view, member_code, post_is_deleted)
values ('테스트글제목1',
        '테스트용 포스트입니다. 이 글은 영국에서부터 시작되서 100년동안 어쩌구 저쩌구',
        'PUBLIC', 0, '2024-03-19 00:00:00', 20000, 1, false),
       ('테스트글제목2',
        '테스트용 포스트입니다. 이 글은 영국에서부터 시작되서 100년동안 어쩌구 저쩌구',
        'PUBLIC', 0, '2024-03-19 00:00:00', 10000, 2, false),
       ('테스트글제목3',
        '테스트용 포스트입니다. 이 글은 영국에서부터 시작되서 100년동안 어쩌구 저쩌구',
        'PUBLIC', 0, '2024-03-19 00:00:00', 70000, 2, false),
       ('테스트글제목4',
        '테스트용 포스트입니다. 이 글은 영국에서부터 시작되서 100년동안 어쩌구 저쩌구',
        'PUBLIC', 0, '2024-03-19 00:00:00', 100, 1, false),
       ('테스트글제목5',
        '테스트용 포스트입니다. 이 글은 영국에서부터 시작되서 100년동안 어쩌구 저쩌구',
        'PUBLIC', 0, '2024-03-19 00:00:00', 20, 2, false),
       ('테스트글제목6',
        '테스트용 포스트입니다. 이 글은 영국에서부터 시작되서 100년동안 어쩌구 저쩌구',
        'PUBLIC', 0, '2024-03-19 00:00:00', 200, 1, false),
       ('테스트글제목7',
        '테스트용 포스트입니다. 이 글은 영국에서부터 시작되서 100년동안 어쩌구 저쩌구',
        'PUBLIC', 0, '2024-03-19 00:00:00', 70, 2, false),
       ('테스트글제목8',
        '테스트용 포스트입니다. 이 글은 영국에서부터 시작되서 100년동안 어쩌구 저쩌구',
        'PUBLIC', 0, '2024-03-19 00:00:00', 1, 1, false),
       ('테스트글제목9',
        '테스트용 포스트입니다. 이 글은 영국에서부터 시작되서 100년동안 어쩌구 저쩌구',
        'PUBLIC', 0, '2024-03-19 00:00:00', 201, 2, false),
       ('테스트글제목10',
        '테스트용 포스트입니다. 이 글은 영국에서부터 시작되서 100년동안 어쩌구 저쩌구',
        'PUBLIC', 0, '2024-03-19 00:00:00', 2024, 1, false);

INSERT INTO `tbl_post-tag`(post_code, tag_code)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (3, 7),
       (3, 8),
       (4, 9),
       (5, 10),
       (5, 11),
       (6, 12),
       (6, 13),
       (7, 14),
       (8, 15),
       (9, 16),
       (10, 17),
       (10, 18),
       (10, 19),
       (10, 20);

INSERT INTO tbl_like_list(post_code, member_code)
VALUES (2, 1),
       (6, 2),
       (8, 2);

INSERT INTO tbl_r_category(r_category_name)
VALUES ('욕설/비방'),
       ('광고/도배'),
       ('성적/음란'),
       ('기타');
INSERT INTO tbl_tissue(order_class, order_date, tissue_price, member_code, post_code)
VALUES ('BUY', '2024-03-19 00:00:00', 1000000, 1, 0);

INSERT INTO tbl_comments(post_code, cmt_text, cmt_wri_date, member_code, parents_code, cmt_mod_date, cmt_dele_date)
VALUES (10,'놀라운 글! 이것은 내개 매우 유용한.','2024-03-19 00:00:00',2,null,null,null);
