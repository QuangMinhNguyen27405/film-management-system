$(document).ready(function() {
    // Add new actor for film forms
    $('#addActorBtn').click(function() {
        $('#actorsContainer').append(
            '<input type="text" class="form-control mb-2" name="actors" placeholder="Actor Name">'
        );
    });


});