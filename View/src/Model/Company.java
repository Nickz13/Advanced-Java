package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.File;

public class Company implements Serializable{
	private long abn;
	private String cid, cname, cadd, curl;

	public Company(String id, String name, long abn, String url, String add) {
		this.cid = id;
		this.abn = abn;
		this.cname = name;
		this.cadd = add;
		this.curl = url;
	}

	public long getAbn() {
		return abn;
	}

	public String getCadd() {
		return cadd;
	}

	public String getCid() {
		return cid;
	}

	public String getCname() {
		return cname;
	}

	public String getCurl() {
		return curl;
	}

}