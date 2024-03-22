INSERT INTO tbl_member
(member_id, member_pwd, member_gender, member_age, member_birth, member_subdate, member_status, have_tissue,
 member_authority)
VALUES ('user02@gmail.com', 'admin', 'MALE', 20, '2012-12-12 00:00:00', '2024-03-19 00:00:00', 'A', 1000000, 'COMMON'),
       ('user03@gmail.com', 'pass01', 'FEMALE', 18, '2021-01-01 00:00:00', '2024-03-19 00:00:00', 'A', 0, 'COMMON'),
       ('user04@gmail.com', 'admin', 'MALE', 20, '2012-12-12 00:00:00', '2024-03-19 00:00:00', 'A', 1000000, 'COMMON'),
       ('user05@gmail.com', 'pass01', 'FEMALE', 18, '2021-01-01 00:00:00', '2024-03-19 00:00:00', 'A', 0, 'COMMON'),
       ('user06@gmail.com', 'admin', 'MALE', 20, '2012-12-12 00:00:00', '2024-03-19 00:00:00', 'A', 1000000, 'COMMON'),
       ('user07@gmail.com', 'pass01', 'FEMALE', 18, '2021-01-01 00:00:00', '2024-03-19 00:00:00', 'A', 0, 'COMMON'),
       ('user08@gmail.com', 'admin', 'MALE', 20, '2012-12-12 00:00:00', '2024-03-19 00:00:00', 'A', 1000000, 'COMMON'),
       ('user09@gmail.com', 'pass01', 'FEMALE', 18, '2021-01-01 00:00:00', '2024-03-19 00:00:00', 'A', 0, 'COMMON'),
       ('user10@gmail.com', 'admin', 'MALE', 20, '2012-12-12 00:00:00', '2024-03-19 00:00:00', 'A', 1000000, 'COMMON'),
       ('user11@gmail.com', 'pass01', 'FEMALE', 18, '2021-01-01 00:00:00', '2024-03-19 00:00:00', 'A', 0, 'COMMON');


INSERT INTO tbl_profile(pr_member_code, profile_nickname, profile_statmsg)
VALUES (3, '유저2', '대충상태메세지'),
       (4, '유저3', '대충상태메세지'),
       (5, '유저4', '대충상태메세지'),
       (6, '유저5', '대충상태메세지'),
       (7, '유저6', '대충상태메세지'),
       (8, '유저7', '대충상태메세지'),
       (9, '유저8', '대충상태메세지'),
       (10, '유저9', '대충상태메세지'),
       (11, '유저10', '대충상태메세지'),
       (12, '유저11', '대충상태메세지');

INSERT INTO tbl_like_list(post_code, l_member_code)
VALUES (2, 5),
       (4, 2),
       (4, 3),
       (4, 4),
       (4, 5),
       (4, 6),
       (4, 7),
       (4, 8),
       (4, 9),
       (4, 10),
       (4, 11),
       (5, 8),
       (5, 7),
       (5, 6),
       (5, 5),
       (5, 4),
       (5, 3),
       (5, 2),
       (5, 1),
       (7, 12),
       (7, 11),
       (7, 10);

# 첨부파일 테스트를 위해 추가
INSERT INTO tbl_attachment(origin_name, safe_name, file_path, post_code)
VALUES ('cat.jpg','cat.jpg','/userUploadFiles',4),
 ('dog.jpg','dog.jpg','/userUploadFiles',7),
 ('tiger.jpg','tiger.jpg','/userUploadFiles',8),
 ('tree.jpg','tree.jpg','/userUploadFiles',5);