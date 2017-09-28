#ElecMatter	待办事宜
create table elecMatter(
	matterId varchar(100) primary key,#逻辑id
	stationRunStatus text,#站点运行状态
	devRunStatus text,#设备运行情况
	createDate date#创建时间
);

#ElecUser用户表
create table elecUser(
	userId varchar(100) primary key,
	account varchar(30) unique,
	password varchar(100),
	username varchar(30),
	gender varchar(20),
	birthday date,
	address varchar(255),
	homeTel varchar(30),
	phone varchar(30),
	email varchar(30),
	isDuty varchar(30),
	units varchar(30),
	onDutyDate date,
	offDutyDate date,
	comment text,
	
	createUser varchar(100),
	createDate date,
	lastUpdateUser varchar(100),
	lastUpdateDate date,
	
	isDelete boolean
);

#elecRole角色表
create table elecRole(
	roleId varchar(100) primary key,
	roleName varchar(50) unique
);

#elecFunction权限表
create table elecFunction(
	functionId varchar(100) primary key,
	functionName varchar(100) not null,
	path varchar(100) unique not null,
	groups varchar(50)
);

#elecUserRole用户角色关联表
create table elecUserRole(
	userId varchar(255),
	roleId varchar(255),
	foreign key (userId) references elecUser(userId) on delete cascade on update cascade,
	foreign key (roleId) references elecRole(roleId) on delete cascade on update cascade
);

#elecRoleFunctioin角色权限关联表
create table elecRoleFunction(
	roleId varchar(255),
	functionId varchar(255),
	foreign key (roleId) references elecRole(roleId) on delete cascade on update cascade,
	foreign key (functionId) references elecFunction(functionId) on delete cascade on update cascade
);
#申请模板表
create table elecApplyTemplate(
	templateId varchar(100),#模板id
	filename varchar(100),#模板文件名
	path text,#模板文件真实路径
	processDefinitionKey varchar(100)#模板表关联的流程定义的key
);

#申请信息表
create table elecApply(
	applyId varchar(100) primary key,#申请信息的逻辑id
	userId varchar(100),#申请人信息
	username varchar(50),	
	account varchar(50),
	processDefinitionId varchar(100),#记录提交申请时对应的特定的流程定义，保证提交申请和审批申请时使用的是同一个流程定义
	processDefinitionKey varchar(50),
	applyTime date,#申请时间
	applyStatus varchar(50),
	filename varchar(100),#申请文件名称
	path text#申请文件的路径
);