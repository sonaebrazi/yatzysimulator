$(document).ready(function() {

    // Start Game functionality
        $('#startGameButton').on('click', function() {
            var playerName = $('#playerName').val(); // Get the player's name from the input field

                if (!playerName) {
                    alert("Please enter your name before starting the game.");
                    return; // Stop if the name is not provided
                }
            $.ajax({
                url: '/start-game/'+encodeURIComponent(playerName), // Encoding the player name,
                method: 'GET',
                success: function(response) {
                    $('#tokenDisplay').text("your token is: "+response.token);
                    $('#roll-token').val(response.token); // Automatically fill the roll dice form with the token
                    $('#category-token').val(response.token) //Automatically fill the calculation form with token
                },
                error: function() {
                    alert("Error starting game.");
                }
            });
        });

    // Handle dice roll form submission
    $(document).ready(function() {
        $('#roll-form').on('submit', function(event) {
            event.preventDefault(); // Prevent the default form submission

            var token = $('#roll-token').val(); // Get the token value
            $.ajax({
                url: '/roll-dice/' + encodeURIComponent(token), // Include the token and category in the URL path
                method: 'GET',
                success: function(response) {
                    $('#dice-values').empty(); // Clear previous dice values
                    response.diceValues.forEach(function(value) {
                        $('#dice-values').append('<li>' + value + '</li>'); // Display each dice value
                    });
                },
                error: function(xhr, status, error) {
                    console.log('Error:', status, error);
                    console.log('Response Text:', xhr.responseText);
                    alert('Failed to roll dice. Please try again.');
                }
            });
        });
    });

    // Handle scores form submission
    $('#scores-form').on('submit', function(event) {
        event.preventDefault(); // Prevent the default form submission

        var token = $('#scores-token').val(); // Get the token value

        $.ajax({
            url: '/scores/'+ encodeURIComponent(token), // Endpoint for getting scores
            method: 'GET',
            success: function(response) {
                $('#scores-list').empty(); // Clear previous results
                var totalScore = 0;

                response.scoreCategoryList.forEach(function(item) {
                    $('#scores-list').append('<li>Category ' + item.category + ': ' + item.score + '</li>');
                    totalScore += item.score; // Add to total score
                });

                $('#total-score').text(totalScore); // Update total score
            },
            error: function() {
                alert('Failed to get scores. Please try again.');
            }
        });
    });

    // Handle category form submission
    $(document).ready(function() {
        $('#category-form').on('submit', function(event) {
            event.preventDefault(); // Prevent the default form submission

            var token = $('#category-token').val(); // Get the token value
            var category = $('#category').val(); // Get the category value

            $.ajax({
                url: '/calculation', // Include the token in the URL
                            method: 'POST',
                            data: JSON.stringify({ token: token, category: category }), // Send token and category as JSON
                            contentType: 'application/json', // Set content type to JSON
                            success: function(response) {
                                $('#category-score').text(response.score); // Update the category score
                            },
                            error: function(xhr, status, error) {
                                console.log('Error:', status, error);
                                console.log('Response Text:', xhr.responseText);
                                alert('Failed to get category score. Please try again.');
                }
            });
        });

        // Get Completed Games functionality
            $('#getCompletedGamesButton').on('click', function() {
                $.ajax({
                    url: '/completed-games',
                    method: 'GET',
                    success: function(response) {
                        var completedGamesTable = $('#completedGamesTable tbody');
                        completedGamesTable.empty(); // Clear previous games
                        response.forEach(function(game) {
                            completedGamesTable.append(
                                '<tr>' +
                                '<td>' + game.token + '</td>' +
                                '<td>' + game.name + '</td>' +
                                '<td>' + game.totalScore + '</td>' +
                                '</tr>'
                            );
                        });
                    },
                    error: function() {
                        alert("Error fetching completed games.");
                    }
                });
            });
    });
    });