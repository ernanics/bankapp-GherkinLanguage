package br.ufsc.model;

import java.util.Date;

public class PinHistory implements Comparable<PinHistory> {
	private boolean result;
	private Date date;
	
	public PinHistory(){
		
	}
	
	public PinHistory(boolean result, Date date){
		this.setResult(result);
		this.setDate(date);
	}
	
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int compareTo(PinHistory o) {
		return getDate().compareTo(o.getDate());
	}
}
