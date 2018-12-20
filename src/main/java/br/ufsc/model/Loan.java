package br.ufsc.model;

import java.math.BigDecimal;

public class Loan {
	private Double amount;
	private Double pcOfInterest;
	private Long numberOfMontlhyInstalments;
	
	public Loan(){
		
	}
	
	public Loan(Double amount, Double pcOfInterest, Long numberOfMontlhyInstalments){
		this.setAmount(amount);
		this.setPcOfInterest(pcOfInterest);
		this.setNumberOfMontlhyInstalments(numberOfMontlhyInstalments);
	}
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getPcOfInterest() {
		return pcOfInterest;
	}
	public void setPcOfInterest(Double pcOfInterest) {
		this.pcOfInterest = pcOfInterest;
	}
	public Long getNumberOfMontlhyInstalments() {
		return numberOfMontlhyInstalments;
	}
	public void setNumberOfMontlhyInstalments(Long numberOfMontlhyInstalments) {
		this.numberOfMontlhyInstalments = numberOfMontlhyInstalments;
	}
	
	private Double calculateValueOfMontlhyInstalments(){
		return this.getAmount()*(this.getPcOfInterest()/100)*
				(Math.pow(1 + (this.getPcOfInterest()/100),this.getNumberOfMontlhyInstalments())/
				(Math.pow(1 + (this.getPcOfInterest()/100),this.getNumberOfMontlhyInstalments())-1));
	}
	
	public BigDecimal getValueOfMontlhyInstalments(){
		BigDecimal result = new BigDecimal(this.calculateValueOfMontlhyInstalments());
		result = result.setScale(2, BigDecimal.ROUND_HALF_UP);
		return result;
	}
	
	public BigDecimal getLoanAmountWithInterests(){
		BigDecimal result = new BigDecimal(this.calculateValueOfMontlhyInstalments()*this.getNumberOfMontlhyInstalments());
		result = result.setScale(2, BigDecimal.ROUND_HALF_UP);
		return result;
	}
}
