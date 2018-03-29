package com.ktds.actionhistory.vo;

public interface ActionHistory {
	
	//인터페이스 내의 다른 인터페이스 .
	static public interface ReqType{
		public static final String VIEW ="view";
		public static final String MEMBER = "mbr";
		public static final String COMMUNITY = "com";
		public static final String UPLOAD = "upl";
		public static final String DOWNLOAD = "dwn";
	}
	
	static public interface Log{
		public static final String VIEW = "View: %s, method: %s";
		public static final String LOGOUT = "Logout: %s";
		public static final String LOGIN = "Login: %s";
		public static final String REGIST = "Regist: Email(%s), NickName(%s), Result(%s)";
		
		public static final String WRITE = "Write: Subject(%s), Body(%s)";
		public static final String UPDATE = "Update: Subject(%s), Body(%s)";
		public static final String DELETE = "Delete: ID(%d), Subject(%s), Body(%s)";
		public static final String READ = "Read: ID(%d), Subject(%s), Body(%s)";
		public static final String RECOMMEND = "Recommend: ID(%d)";
		public static final String WRITE_REPLY = "Write Reply: CommunityID(%d),Body(%s)";
		
		public static final String DOWNLOAD = "Download:CommunityID(%d), Flilename(%s) ";
		public static final String UPLOAD = "Upload: Filename(%s)";
	}
}
