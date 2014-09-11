   $(document)
            .ready(
                    function() {
                        //add more file components if Add is clicked
                        $('#addFile')
                                .click(
                                        function() {
                                            $('#fileTable')
                                                    .append(
                                                            '<tr><td>'
                                                                    + '   <input type="file" name="file" />'
                                                                    + '</td></tr>');
                                        });
 
                    });