create table if not exists author (
  author_id int auto_increment not null comment '作者id',
  name nvarchar(100) not null comment '作者名字',
  create_time timestamp default  current_timestamp comment '创建时间',
  update_time timestamp default current_timestamp on update current_timestamp comment '更新时间',
  index (update_time, author_id),
  primary key (author_id)
) comment '作者表';

create table if not exists directory (
  dir_id int auto_increment not null comment '目录id',
  name nvarchar(100) not null comment '目录名称',
  create_time timestamp default  current_timestamp comment '创建时间',
  update_time timestamp default current_timestamp on update current_timestamp comment '更新时间',
  primary key (dir_id)
) comment '文件夹表';

create table if not exists directory_path (
  ancestor int not null comment '祖先目录id',
  dir_id int not null comment '后代目录id',
  path_length int not null comment '目录的深度',
  create_time timestamp default  current_timestamp comment '创建时间',
  update_time timestamp default current_timestamp on update current_timestamp comment '更新时间',
  primary key (ancestor, dir_id)
) comment '文件夹路径表';

create table if not exists book (
  book_id int auto_increment not null comment '书本id',
  title nvarchar(200) not null comment '书本名称',
  dir_id int not null comment '书本所在目录',
  language nvarchar(50) null comment '书本使用的语言',
  series nvarchar(100) null comment '书本所属的系列',
  publisher nvarchar(50) null comment '出版社',
  publish_time timestamp null comment '出版时间',
  rating smallint null comment '评价',
  isbn10 bigint(10) null comment 'isbn10',
  isbn13 bigint(13) null comment 'isbn13',
  page smallint null comment '页数',
  format char(4) not null comment '书本格式',
  create_time timestamp default  current_timestamp comment '创建时间',
  update_time timestamp default current_timestamp on update current_timestamp comment '更新时间',
  index (update_time, book_id),
  primary key (book_id)
) comment '书籍表';

create table if not exists book_cover (
  book_id int not null comment '书本id',
  cover mediumblob not null comment '封面二进制数据',
  image_file_extension varchar(10) not null comment '封面图片扩展名',
  primary key (book_id)
) comment '书籍封面表';

create table if not exists author_book_relation (
  book_id int not null comment '书本id',
  author_id int not null comment '作者id',
  create_time timestamp default  current_timestamp comment '创建时间',
  update_time timestamp default current_timestamp on update current_timestamp comment '更新时间',
  primary key (book_id, author_id)
) comment '作者书籍关系表';

create table media(
  media_id varchar(100) not null comment '文件id',
  content mediumblob not null comment '文件内容',
  media_type varchar(10) not null comment '文件类型',
  file_extension varchar(10) not null comment '文件扩展名',
  primary key (media_id)
) comment '多媒体文件表(如: 图片, 语音等)';

create table if not exists property (
  prop_name varchar(50) not null comment '属性名称',
  prop_value nvarchar(200) not null comment '属性值',
  create_time timestamp default  current_timestamp comment '创建时间',
  update_time timestamp default current_timestamp on update current_timestamp comment '更新时间',
  primary key (prop_name)
) comment '属性表';

