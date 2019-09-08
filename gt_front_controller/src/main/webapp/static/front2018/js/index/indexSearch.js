    $(".lw_tab").click(function(event) {
         var coin = $("#search").val();
         coin = coin.toUpperCase();

        var a = $(this);
        var key = a.data().key;
        if(coin == ''){
            if(key!=0){
                $(".market-con").css('display','none'); 
                $("."+key+"_market_list").css('display','block'); 
            }else{
                $(".market-con").css('display','block'); 
            }
        }else{
            $(".market-con").css('display','none'); 
            $(".market_show").each(function(){
                if($(this).data('key') == ''){
                    return true;
                }else{

                    if($(this).data('key').indexOf(coin) != -1){
                        if(key != 0){
                            if(key == $(this).data("mark")){
                                $(this).show();
                            }
                        }else{
                            $(this).show();
                        }                                
                    }      
                } 
            });

        }
        $(".lw_tab").removeClass("active");
        $("#"+key+"_market").addClass("active");
    });
    
    $("#search").on('input',(function(){
        var coin = $("#search").val();
        coin = coin.toUpperCase();
        var key = $(".tabs li.active").data('key');
        $(".market-con").css('display','none'); 
        if(coin == ''){
            if(key!=0){
            $(".market-con").css('display','none'); 
            $("."+key+"_market_list").css('display','block'); 
            }else{
                $(".market-con").css('display','block'); 
            }
        }else{
            
            $(".market_show").each(function(){
            if($(this).data('key') == ''){
                return true;
            }else{
               
                if($(this).data('key').indexOf(coin) != -1){
                    if(key != 0){
                        if(key == $(this).data("mark")){
                            $(this).show();
                        }
                        // $("."+key+"_market_list").css('display','block').addClass("markent_s").removeClass("markent_h"); 
                    }else{
                        $(this).show();
                    }
                        
                    
                    
                }
                    
            }
            
        });
      }
    }));


$(function(){ 

    $('.search .text').on({ 
        focus:function(){ 
            if (this.value == this.defaultValue){ 
                this.value=""; 
            } 
        }, 
        blur:function(){ 
            if (this.value == ""){ 
                this.value = this.defaultValue; 
            } 
        } 
    }); 
}) 
