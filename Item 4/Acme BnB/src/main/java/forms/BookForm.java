package forms;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


public class BookForm {

	private int propertyId;
	private Date checkInDate;
	private Date checkOutDate;
	private boolean smoker;
	private String holderName;
	private String brandName;
	private String number;
	private int expirationMonth;
	private int expirationYear;
	private int cvvCode;
	
	public int getPropertyId() {
		return propertyId;
	}
	
	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCheckInDate() {
		return checkInDate;
	}
	
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	
	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	
	public boolean getSmoker() {
		return smoker;
	}
	
	public void setSmoker(boolean smoker) {
		this.smoker = smoker;
	}
	
	public String getHolderName() {
		return holderName;
	}
	
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	
	public String getBrandName() {
		return brandName;
	}
	
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public int getExpirationMonth() {
		return expirationMonth;
	}
	
	public void setExpirationMonth(int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	
	public int getExpirationYear() {
		return expirationYear;
	}
	
	public void setExpirationYear(int expirationYear) {
		this.expirationYear = expirationYear;
	}
	
	public int getCvvCode() {
		return cvvCode;
	}
	
	public void setCvvCode(int cvvCode) {
		this.cvvCode = cvvCode;
	}
	
}
