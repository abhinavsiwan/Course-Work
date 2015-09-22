<!DOCTYPE html>
<html>
    <head>
        <title>PHP form Validation</title>
        <style type="text/css">
            .error {color:red;}
        </style>
    </head>
    
    <body>
        <h1>PHP Form validation Example</h1>
        <p class="error">* required field</p>
            <form method="post" name="form1" id="form1" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"];?>">
        <!-- To show the values in the input field after the user hits the submit button, we need to put some php code in the value field of the input fields -->
                <label>Name:</label><input type="text" name="name" id="name" value="<?php echo $name;?>" \>
                <span class="error">* <?php echo $nameERR;?></span>
                <br /><br />
                <label>E-mail:</label><input type="text" name="email" id="email" value="<?php echo $email;?>" \>
                <span class="error">* <?php echo $emailERR;?></span>
                <br /><br />
                <label>Website:</label><input type="text" name="website" id="website" value="<?php echo $website;?>" \>
                <label>Comments:</label><textarea name="comments" rows="5" cols="50"><?php echo $comments;?></textarea>
                
                <label>Gender:</label><input type="radio" name="male" value="male" <?php if(isset($gender) && ($gender)=="male") echo "checked";?>/><label>Male</label>
                <label>Gender:</label><input type="radio" name="female" value="female" <?php if(isset($gender) && ($gender)=="female") echo "checked";?>/><label>Female</label>
                <span class="error">* <?php echo $genderERR;?></span>
                <br /><br />
                
                <input type="submit" value="Submit form"/>
                
            </form>
        
        <?php
            $name,$email,$website,$comments,$gender="";
            $nameERR,$emailERR,$websiteERR,$commentsERR,$genderERR="";
                                                                                            
            if($_SERVER["REQUEST_METHOD"]=="POST")
            {
                if(empty($_POST["name"]))
                   $nameERR = "Name is required";
                else
                {
                    $name=test_input($_POST["name"]);
                    if(!preg_match("/^[A-Z][a-z]*$/",$name)
                        $nameERR="Only letters and whitespace allowed";
                }
                   
                if(empty($_POST["email"]))
                   $emailERR = "Email is required";
                else
                {
                    email=test_input($_POST["email"]);
                    if(!filter_var($email, FILTER_VALIDATE_EMAIL))
                        $emailERR="Invalid email format";
                 }
                       
                   
                if(empty($_POST["website"]))
                   $websiteERR = "URL is required";
                else
                   $website=test_input($_POST["website"]);
                       
                       
                if(empty($_POST["comments"]))
                   $commentsERR = "";
                else
                   $comments=test_input($_POST["comments"]);
                       
                   
                if(empty($_POST["gender"]))
                   $genderERR = "Gender is required";
                else
                   $gender=test_input($_POST["gender"]);
                   
            }
            
            function test_input($data)
            {
                $data=trim($data);
                $data=stripslashes($data);
                $data=htmlspecialchars($data);
                return $data;
            }
                       
                   
        ?>
    </body>
</html>