package management.employee.salesEmployee.model;

import java.math.BigDecimal;

public enum Position {

	SALES_ASSOCIATE(BigDecimal.valueOf(5), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO){
		@Override
		public BigDecimal computeCommission(BigDecimal salesAmount) {
			return calculateAmountUsingPercentage(salesAmount, commission);
		}
	},
	SENIOR_SALES_ASSOCIATE(BigDecimal.valueOf(7), BigDecimal.valueOf(2), BigDecimal.valueOf(50000), BigDecimal.ZERO){
		@Override
		public BigDecimal computeCommission(BigDecimal salesAmount) {
			BigDecimal totalCommission = Position.calculateAmountUsingPercentage(salesAmount, commission);
			if(salesAmount.compareTo(threshold) > 0){
				totalCommission = totalCommission.add(Position.calculateAmountUsingPercentage(salesAmount.subtract(threshold), additionalCommission));
			}
			return totalCommission;
		}
	},
	SALES_MANAGER(BigDecimal.valueOf(6), BigDecimal.ZERO, BigDecimal.valueOf(200000), BigDecimal.valueOf(1000)){
		@Override
		public BigDecimal computeCommission(BigDecimal salesAmount) {
			BigDecimal totalCommission = Position.calculateAmountUsingPercentage(salesAmount, commission);
			if(salesAmount.compareTo(threshold) > 0){
				totalCommission = totalCommission.add(fixedRateBonus);
			}
			return totalCommission;
		}
	};

	BigDecimal commission;
	BigDecimal additionalCommission;
	BigDecimal threshold;
	BigDecimal fixedRateBonus;

	Position(BigDecimal commission, BigDecimal additionalCommission, BigDecimal threshold,
             BigDecimal fixedRateBonus) {
		this.commission = commission;
		this.additionalCommission = additionalCommission;
		this.threshold = threshold;
		this.fixedRateBonus = fixedRateBonus;
	}

	public abstract BigDecimal computeCommission(BigDecimal salesAmount);
	private static BigDecimal calculateAmountUsingPercentage(BigDecimal amount, BigDecimal percentage){
		return (amount.multiply(percentage)).divide(BigDecimal.valueOf(100));
	}

}
