<html>
    <head>
        <title>Favorite fruit selection form</title>
    </head>
    <body>
        <form action="/favorite_fruit" method="POST">
            <p>What is your favorite fruit?</p>
            <#list fruits as fruit>
            <p>
                <INPUT type="radio" name="fruit" value="${fruit}">${fruit}</input>
            </p>
        </#list>
        <input type="submit" value="Submit"/>

    </form>
</body>
        </html>