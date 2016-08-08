
$(".p-minus, .p-plus").click(function () {
	var $button = $(this);
    var oldValue = $button.closest('.p-quantity').find(".p-input input").val();

    if ($button.text() == "+") {
    	 if (oldValue < 20) {
             var newVal = Number(oldValue) + 1;
         } else {
             newVal = 20;
         }
    } else {
        if (oldValue > 1) {
            var newVal = Number(oldValue) - 1;
        } else {
            newVal = 1;
        }
    }
    $button.closest('.p-quantity').find(".p-input input").val(newVal);
});
