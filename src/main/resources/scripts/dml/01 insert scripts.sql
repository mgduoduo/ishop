-- tb_user
insert into tb_user(user_name, pass_word, expiry_ind) values('abc', 'ec0405c5aef93e771cd80e0db180b88b','N');

-- tb_code_int
insert into tb_code_int(codetype_id, code_id, code_desc) values('CMN_YES_NO','Y', 'YES');
insert into tb_code_int(codetype_id, code_id, code_desc) values('CMN_YES_NO','N', 'NO');
insert into tb_code_int(codetype_id, code_id, code_desc) values('CMN_YES_NO_NA','Y', 'YES');
insert into tb_code_int(codetype_id, code_id, code_desc) values('CMN_YES_NO_NA','N', 'NO');
insert into tb_code_int(codetype_id, code_id, code_desc) values('CMN_YES_NO_NA','NA', 'N.A.');

insert into tb_code_int(codetype_id, code_id, code_desc) values('CONTACT_TP','QQ', 'QQ');
insert into tb_code_int(codetype_id, code_id, code_desc) values('CONTACT_TP','WW', 'WW');
insert into tb_code_int(codetype_id, code_id, code_desc) values('CONTACT_TP','MP', 'MobilePhone');
insert into tb_code_int(codetype_id, code_id, code_desc) values('CONTACT_TP','OT', 'Other');

insert into tb_code_int(codetype_id, code_id, code_desc) values('PAY_TP','A', 'huo dao fu kuan');
insert into tb_code_int(codetype_id, code_id, code_desc) values('PAY_TP','B', 'kuan dao fa huo');
insert into tb_code_int(codetype_id, code_id, code_desc) values('PAY_TP','C', 'zhi fu bao');

insert into tb_code_int(codetype_id, code_id, code_desc) values('EXPRESS_STATUS','A', 'dai pei fa');
insert into tb_code_int(codetype_id, code_id, code_desc) values('EXPRESS_STATUS','B', 'fa chu tu zhong');
insert into tb_code_int(codetype_id, code_id, code_desc) values('EXPRESS_STATUS','C', 'yi qian shou');


insert into tb_code_int(codetype_id, code_id, code_desc) values('EXPRESS_COMPANY','A', 'zhong tong');
insert into tb_code_int(codetype_id, code_id, code_desc) values('EXPRESS_COMPANY','B', 'yuan tong');
insert into tb_code_int(codetype_id, code_id, code_desc) values('EXPRESS_COMPANY','C', 'you zheng');


insert into tb_code_int(codetype_id, code_id, code_desc) values('PROD_CODE','Product01', 'product 01');
insert into tb_code_int(codetype_id, code_id, code_desc) values('PROD_CODE','Product02', 'product 02');
insert into tb_code_int(codetype_id, code_id, code_desc) values('PROD_CODE','Product03', 'product 03');