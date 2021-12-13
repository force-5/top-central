var Ajax;
if (Ajax && (Ajax != null)) {
	Ajax.Responders.register({
	  onCreate: function() {
        if($('spinner') && Ajax.activeRequestCount>0)
          Effect.Appear('spinner',{duration:0.5,queue:'end'});
	  },
	  onComplete: function() {
        if($('spinner') && Ajax.activeRequestCount==0)
          Effect.Fade('spinner',{duration:0.5,queue:'end'});
	  }
	});
}


// Code to display the custom file-tree for Public Access Screen
(function ($) {
    $.fn.disableSelection = function () {
        return this.each(function () {
            if (typeof this.onselectstart != 'undefined') {
                this.onselectstart = function() {
                    return false;
                };
            } else if (typeof this.style.MozUserSelect != 'undefined') {
                this.style.MozUserSelect = 'none';
            } else {
                this.onmousedown = function() {
                    return false;
                };
            }
        });
    };
})(jQuery);


