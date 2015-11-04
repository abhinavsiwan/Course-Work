function validate() 
{
    //alert("I am here");
    $.ajax({
        url: 'HW8.php',
        type: 'post',
        data: {
                address: $('#addressid').val(),
                city: $('#cityid').val(),
                state: $('#stateid').val(),
                degree: $('input[name=degree]:checked').val()
            },
        success: function(output) {
            transform(output);
        }
    });
}

function unit_temp(temp)
{
    //alert("I am here");
    if(document.getElementById("us").checked)
    {
        temp=temp+"&deg; F";
    }
    if(document.getElementById("si").checked)
    {
        temp=temp+"&deg; C  ";
    }
    return temp;
}

function unit_precip(precipitation1)
{
    var precip;
    if(document.getElementById("us").checked)
    {
        precipitation1=precipitation1;
    }
    if(document.getElementById("si").checked)
    {
        precipitation1=precipitation1 / 25.4;
    }
    
    //Mapping the precipitation values
    if(precipitation1>=0 && precipitation1<0.002)
        precip="None";
    if(precipitation1>=0.002 && precipitation1<0.017)   
        precip="Very Light";
    if(precipitation1>=0.017 && precipitation1<0.1)
        precip="Light";
    if(precipitation1>=0.1 && precipitation1<0.4)
        precip="Moderate";
    if(precipitation1>=0.4)
        precip="Heavy";
    
    return precip;
}

function unit_wind(wind)
{
    if(document.getElementById("us").checked)
    {
        wind=wind+" mph";
    }
    if(document.getElementById("si").checked)
    {
        wind=wind+" mpsec";
    }
    return wind;
}

function unit_dew(dew)
{
    if(document.getElementById("us").checked)
    {
        dew=dew+"&deg; F";
    }
    if(document.getElementById("si").checked)
    {
        dew=dew+"&deg; C";
    }
    return dew;
}

function unit_visi(visi)
{
    if(document.getElementById("us").checked)
    {
        visi=visi+" mi";
    }
    if(document.getElementById("si").checked)
    {
        visi=visi+" km";
    }
    return visi;
}

function unit_press(press)
{
    if(document.getElementById("us").checked)
    {
        press=press+" mb";
    }
    if(document.getElementById("si").checked)
    {
        press=press+" hPa";
    }
    return press;
}

function convertTimestamp(timestamp) {
  var d = new Date(timestamp * 1000),	// Convert the passed timestamp to milliseconds
		yyyy = d.getFullYear(),
		mm = ('0' + (d.getMonth() + 1)).slice(-2),	// Months are zero based. Add leading 0.
		dd = ('0' + d.getDate()).slice(-2),			// Add leading 0.
		hh = d.getHours(),
		h = hh,
		min = ('0' + d.getMinutes()).slice(-2),		// Add leading 0.
		ampm = 'AM',
		time;
			
	if (hh > 12) {
		h = hh - 12;
		ampm = 'PM';
	} else if (hh === 12) {
		h = 12;
		ampm = 'PM';
	} else if (hh == 0) {
		h = 12;
	}
	
	// ie: 2013-02-18, 8:35 AM	
	time = yyyy + '-' + mm + '-' + dd + ', ' + h + ':' + min + ' ' + ampm;
	time1 = h + ':' + min + ' ' + ampm;	
	return time1;
}

function map_icon(icon)
{
    //Associative array to map the images
    var image= [];
    image['clear-day'] = "clear.png";
    image['clear-night'] = "clear_night.png";
    image['rain'] = "rain.png";
    image['snow'] = "snow.png";
    image['sleet'] = "sleet.png";
    image['wind'] = "wind.png";
    image['fog'] = "fog.png";
    image['cloudy'] = "cloudy.png";
    image['partly-cloudy-day'] = "cloud_day.png";
    image['partly-cloudy-night'] = "cloud_night.png";
    
    return image[icon];
}

var degree;
function transform(output)
{
    //alert("I am in Transform");
    document.getElementById("return").style.visibility="visible";
    var json_o=JSON.parse(output);
    var tab;
    
    //get the city from form element
    var city=document.getElementById("cityid").value;
    //get the state from form element
    var e = document.getElementById("stateid");
    var state = e.options[e.selectedIndex].value;
    
    //get the degree value
    if (document.getElementById('us').checked) 
    {
        degree="&deg; F";
    }
    if (document.getElementById('si').checked) 
    {
        degree="&deg; C";
    }
    
    //For Right Now Tab
    //Weather Condition
    if(json_o.currently.summary!=null)
        weather_condition1=json_o.currently.summary;
    else
        weather_condition1=null;
    
    //Weather Temperature
    if(json_o.currently.temperature!=null)
    {
        temp=Math.round(json_o.currently.temperature);
        temperature1=unit_temp(temp);
    }
    else
        temperature1=null;
    
    //Icon
    var dir="http://cs-server.usc.edu:45678/hw/hw8/images/";
    
    if(json_o.currently.icon!=null)
    {
        //icon1=image[json_o.currently.icon]; 
        icon1=map_icon(json_o.currently.icon); 
        icon_img=dir+icon1;
    }
    else
        icon1=null;
    
    //Precipitation
    if(json_o.currently.precipIntensity!=null)
    {
        precipitation1=unit_precip(json_o.currently.precipIntensity);
    }
    else
        precipitation1=null;
    
    //Chance of Rain
    if(json_o.currently.precipProbability!=null)
    {
        rain1=Math.round(json_o.currently.precipProbability*100)+"%";
    }
    else
        rain1=null;
    
    //Wind Speed
    if(json_o.currently.windSpeed!=null)
    {
        wind=Math.round(json_o.currently.windSpeed);
        wind_speed1=unit_wind(wind);
    }
    else
        wind_speed1=null;

    //Dew Point
    if(json_o.currently.dewPoint!=null)
    {
        dew=Math.round(json_o.currently.dewPoint);
        dew_point1=unit_dew(dew);
    }
    else
        dew_point1=null;
    
    //Humidity
    if(json_o.currently.humidity!=null)
    {
        humidity1=Math.round(json_o.currently.humidity*100)+"%";
    }
    else
        humidity1=null;
    
    //Visibility
    if(json_o.currently.visibility!=null)
    {
        visi=Math.round(json_o.currently.visibility);
        visibility1=unit_visi(visi);
    }
    else
        visibility1=null;
    
    //Sunrise
    if(json_o.daily.data[0].sunriseTime!=null)
    {
        sunrise1=convertTimestamp(json_o.daily.data[0].sunriseTime);
    }
    else
        sunrise1=null;
    
    //Sunset
    if(json_o.daily.data[0].sunsetTime!=null)
    {
        sunset1=convertTimestamp(json_o.daily.data[0].sunsetTime);
    }
    else
        sunset1=null;
    
    if(json_o.daily.data[0].temperatureMax!=null)
    {
        tempMa=Math.round(json_o.daily.data[0].temperatureMax);
        tempMax=unit_temp(tempMa);
    }
    else
        tempMax=null;
    
    if(json_o.daily.data[0].temperatureMin!=null)
    {
        tempMi=Math.round(json_o.daily.data[0].temperatureMin);
        tempMin=unit_temp(tempMi);
    }
    else
        tempMin=null;
    
    var fb_icon="http://cs-server.usc.edu:45678/hw/hw8/images/fb_icon.png";
    var text="<table class=\"table one\">";
     text+="<tr><th colspan=\"3\" rowspan=\"3\" style=\"text-align:center;\"><img src='"+icon_img+"' alt='Weather Pic' width=\"80px\" height=\"80px\" title='"+weather_condition1+"'></th>";
    text+="<th colspan=\"3\" style=\"text-align:center;color:white;\">"+weather_condition1+" in "+city+","+state+"</th></tr>";
    text+="<tr><th colspan=\"3\" style=\"text-align:center;font-size:36px;color:white;\">"+temperature1+"</th></tr>";
    text+="<tr><th colspan=\"2\" style=\"text-align:right;color:white;\">"+"L:"+tempMax+"|H:"+tempMin+"</th><th style=\"text-align:right\"><img src='"+fb_icon+"' alt='FB icon' width=\"30px\" height=\"30px\"></th></tr>";
    text+="</table>";
    
    text+="<table class=\"table two\">";
    text+="<tr class=\"active\"><td>Precipitation:</td><td>"+precipitation1+"</td></tr>";
    text+="<tr class=\"danger\"><td>Chance of Rain:</td><td>"+rain1+"</td></tr>";
    text+="<tr class=\"active\"><td>Wind Speed:</td><td>"+wind_speed1+"</td></tr>";
    text+="<tr class=\"danger\"><td>Dew Point:</td><td>"+dew_point1+"</td></tr>";
    text+="<tr class=\"active\"><td>Humidity:</td><td>"+humidity1+"</td></tr>";
    text+="<tr class=\"danger\"><td>visibility:</td><td>"+visibility1+"</td></tr>";
    text+="<tr class=\"active\"><td>Sunrise:</td><td>"+sunrise1+"</td></tr>";
    text+="<tr class=\"danger\"><td>Sunset:</td><td>"+sunset1+"</td></tr>";
    document.getElementById("RightNow").innerHTML=text;
    
    /*document.getElementById("RightNow").innerHTML=weather_condition1+"<br>"+temperature1+"<br>"+icon_img+"<br>"+precipitation1+"<br>"+rain1+"<br>"+wind_speed1+"<br>"+dew_point1+"<br>"+humidity1+"<br>"+visibility1+"<br>"+sunrise1+"<br>"+sunset1; */
    
    function clickHandler(e){
        tab=e.target.id;
        //alert(tab);
        
        //For Next24 hours tab
        if(tab=="next24")
        {
            if(json_o.hourly.summary!=null)
                weather_condition2=json_o.hourly.summary;
            else
                weather_condition2=null;
            
            //24 hour time
            var time = [];
            for(i=0;i<24;i++)
            {
                if(json_o.hourly.data[i+1].time!=null)
                    time[i]=convertTimestamp(json_o.hourly.data[i+1].time);
                else
                    time[i]=null;
            }
            
            //24 hour summary
            var icon = [];
            for(i=0;i<24;i++)
            {
                if(json_o.hourly.data[i+1].icon!=null)
                {
                    icon1=map_icon(json_o.hourly.data[i+1].icon);
                    icon[i]=dir+icon1;
                }
                else
                    icon[i]=null;
            }
            
            //24 hour Cloud cover
            var cloud = [];
            for(i=0;i<24;i++)
            {
                if(json_o.hourly.data[i+1].cloudCover!=null)
                {
                    cloud[i]=Math.round(json_o.hourly.data[i+1].cloudCover*100)+"%";
                }
                else
                    cloud[i]=null;
            }
            
            //24 hour Temperature
            var temp = [];
            for(i=0;i<24;i++)
            {
                if(json_o.hourly.data[i+1].temperature!=null)
                {
                    tempe=Math.round(json_o.hourly.data[i+1].temperature);
                    temp[i]=unit_temp(tempe);
                }
                else
                    temp[i]=null;
            }
            
            //These needs to be put in glyphicons
            //24 hour Wind
            var wind = [];
            for(i=0;i<24;i++)
            {
                if(json_o.hourly.data[i+1].windSpeed!=null)
                {
                    win=Math.round(json_o.hourly.data[i+1].windSpeed);
                    wind[i]=unit_wind(win);
                }
                else
                    wind[i]=null;
            }
            
            //24 hour Humidity
            var humid = [];
            for(i=0;i<24;i++)
            {
                if(json_o.hourly.data[i+1].humidity!=null)
                {
                    humid[i]=Math.round(json_o.hourly.data[i+1].humidity*100)+"%";
                }
                else
                    humid[i]=null;
            }
            
            //24 hour Visibility
            var visi = [];
            for(i=0;i<24;i++)
            {
                if(json_o.hourly.data[i+1].visibility!=null)
                {
                    vis=Math.round(json_o.hourly.data[i+1].visibility);
                    visi[i]=unit_visi(vis);
                }
                else
                    visi[i]=null;
            }
            
             //24 hour Pressure
            var press = [];
            for(i=0;i<24;i++)
            {
                if(json_o.hourly.data[i+1].pressure!=null)
                {
                    press[i]=unit_press(json_o.hourly.data[i+1].pressure);
                }
                else
                    press[i]=null;
            }
            
            var text24="<table class=\"table\">";
            text24+="<tr style=\"color:white;background:#333399;\"><th style=\"text-align:center;\">Time</th><th style=\"text-align:center;\">Summary</th><th style=\"text-align:center;\">Cloud Cover</th><th style=\"text-align:center;\">Temp("+degree+")</th><th style=\"text-align:center;\">View Details</th></tr>";
            for(i=0;i<24;i++)
            {
                text24+="<tr class=\"active\"><td style=\"text-align:center;\">"+time[i]+"</td><td style=\"text-align:center;\"><img src='"+icon[i]+"' alt='Icon Pic' height=\"20px\" width=\"20px\"></td><td style=\"text-align:center;\">"+cloud[i]+"</td><td style=\"text-align:center;\">"+temp[i]+"</td><td style=\"text-align:center;\"><span class=\"glyphicon glyphicon-plus\" style=\"color:blue;\"></span></td></tr>";
            }
            document.getElementById("Next24").innerHTML=text24;
            
            /*document.getElementById("Next24").innerHTML=time+"<br>"+icon+"<br>"+cloud+"<br>"+temp+"<br>"+wind+"<br>"+humid+"<br>"+visi+"<br>"+press; */
        } 
    
        //For Next7 days tab
        if(tab=="next7")
        {
            if(json_o.daily.summary!=null)
                weather_condition3=json_o.daily.summary;
            else
                weather_condition3=null;
            
            if(json_o.daily.temperature!=null)
                temperature3=json_o.daily.temperature;
            else
                temperature3=null;
            
            document.getElementById("Next7").innerHTML="In next 7 "+"<br>"+weather_condition3+"<br>"+temperature3;
        }
    }
    //document.getElementById("rightnow").onclick=clickHandler;
    document.getElementById("next24").onclick=clickHandler;
    document.getElementById("next7").onclick=clickHandler; 
}