
turnPage = function (pageNumber,flag,tagId,url) {
    if (flag=='pre'){
        pageNumber--;
    } else if (flag=='next') {
        pageNumber++;
    }

    $("#content").load(url+'?page='+pageNumber+'&tagId='+tagId);



};
