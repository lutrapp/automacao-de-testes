				
// ------------------------------- Get Currency Formatted Value ------------------------------- //
function getCurrencyFormatted(value){

	return Intl.NumberFormat('pt-BR', {
  										minimumFractionDigits: 2,
  										maximumFractionDigits: 2,
										useGrouping:true
							}).format(value);
					
}