$(document).ready(
    function() {
      $('#addMoreItem').click(
          function() {
              $('#fileTable').append(
              '<tr>'
              + '   <td><input name="name" type="text" placeholder="item name"        /></td>'
              + '   <td><input name="owner" type="text"  placeholder = "owner"        /></td>'
              + '   <td><input name="date" type="text" id="datepicker" placeholder = "buy date"   /></td>'
              + '   <td><input name="file" type="file"  placeholder = "file"          /></td>'
            +'</tr>');
           });
 
    });