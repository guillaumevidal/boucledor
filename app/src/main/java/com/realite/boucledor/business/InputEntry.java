package com.realite.boucledor.business;

import java.util.Date;

public class InputEntry implements Comparable<InputEntry>{

	private static final String SEP = " | ";
	private final Date inputDate;
	private final double rawAmt;
	private int id;
	private Double retroAmt;
	private double retroPercent;
	private Double afterRetroAmt;
	private double taxPercent;
	private Double taxAmt;
	private Double netAmt;
	private String type;

	public InputEntry(Builder builder) {
		this.id = builder.id;
		this.inputDate = builder.inputDate;
		this.rawAmt = builder.rawAmt;
		this.retroAmt = builder.retroAmt;
		this.retroPercent = builder.retroPercent;
		this.afterRetroAmt = builder.afterRetroAmt;
		this.taxPercent = builder.taxPercent;
		this.taxAmt = builder.taxAmt;
		this.netAmt = builder.netAmt;
		this.type = builder.type;
	}

	@Override
	public int compareTo(InputEntry otherEntry) {
		int dateCompare = inputDate.compareTo(otherEntry.getInputDate());
		return dateCompare == 0 ? (int)(rawAmt - otherEntry.getRawAmt()) : dateCompare;
	}

	public static class Builder {
		private final Date inputDate;
		private final double rawAmt;
		private int id;
		private Double retroAmt;
		private double retroPercent;
		private Double afterRetroAmt;
		private double taxPercent;
		private Double taxAmt;
		private Double netAmt;
		private String type;

		public Builder(Date inputDate, Double rawAmt) {
			this.inputDate = inputDate;
			this.rawAmt = rawAmt;
		}

		public Builder id(int id) {
			this.id = id;
			return this;
		}

		public Builder retroAmt(double retroAmt) {
			this.retroAmt = retroAmt;
			return this;
		}

		public Builder retroPercent(double retroPercent) {
			this.retroPercent = retroPercent;
			return this;
		}

		public Builder afterRetroAmt(Double afterRetroAmt) {
			this.afterRetroAmt = afterRetroAmt;
			return this;
		}

		public Builder taxPercent(double taxPercent) {
			this.taxPercent = taxPercent;
			return this;
		}

		public Builder taxAmt(Double taxAmt) {
			this.taxAmt = taxAmt;
			return this;
		}

		public Builder netAmt(Double netAmt) {
			this.netAmt = netAmt;
			return this;
		}

		public Builder type(String type) {
			this.type = type;
			return this;
		}

		public InputEntry build() {
			return new InputEntry(this);
		}
	}

	public int getId() {
		return id;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public double getRawAmt() {
		return rawAmt;
	}

	public Double getRetroAmt() {
		return retroAmt;
	}

	public double getRetroPercent() {
		return retroPercent;
	}

	public Double getAfterRetroAmt() {
		return afterRetroAmt;
	}

	public double getTaxPercent() {
		return taxPercent;
	}

	public Double getTaxAmt() {
		return taxAmt;
	}

	public Double getNetAmt() {
		return netAmt;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "ID " + id
				+ SEP + inputDate
				+ SEP + rawAmt
				+ SEP + type;
	}
}
