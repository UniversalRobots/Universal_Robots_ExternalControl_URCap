# URCaps External Control
The External Control URCap is the user interface for the Universal Robots [ROS](https://github.com/UniversalRobots/Universal_Robots_ROS_Driver), [ROS2](https://github.com/UniversalRobots/Universal_Robots_ROS2_Driver) and [Isaac SDK](https://github.com/UniversalRobots/Universal_Robots_Isaac_Driver) driver, as well as the [Universal Robots Client Library](https://github.com/UniversalRobots/Universal_Robots_Client_Library) used by the drivers.

It supports the Universal Robots CB3 and e-Series robots.

## Prerequisites
As this URCap is using swing to implement the user interface, the URCap library in version 1.3.0 or
higher is required. Therefore the minimal PolyScope versions are 3.7 and 5.1.

## Usage
* In the _Installation_ tab of Polyscope:
	* Adjust the IP address of your robot in the _Installation_ tab of Polyscope (this step might be unnecessary in simulation). 
* On the remote PC:
	* Launch the suitable _launch_ file for UR3/UR5/UR10 and CB3/e-series.
* In the _Program_ tab of Polyscope:
	* Add this URcap to a program by selecting it from the side menu under the tab _URcap_.
	* Execute the program by pressing the _play_ button in the _Program_ tab of Polyscope.

### Multiple URCap nodes
To use this URCap node multiple times in a ur program, the control script is divided into two
scripts. After receiving the script, it is divided into a header part and a control loop part. The
header part consist of all the function deffinitions. The header is only inserted once in the 
program, while the control loop is inserted for each URCap node in the program tree.

To be able to distinguish between header and control loop, the header part of the script should be
encapsulated in:
```bash
# HEADER_BEGIN
Here goes the header code
# HEADER_END

# NODE_CONTROL_LOOP_BEGINS
Here goes the control loop code
# NODE_CONTROL_LOOP_ENDS
```
If its not possible to find either `# HEADER_BEGIN` or `# HEADER_END`, the script will not be
divided into two scripts and it will not be possible to have multiple URCap nodes in one program.

## Acknowledgment
Developed in collaboration between:

[<img height="60" alt="Universal Robots A/S" src="doc/resources/ur_logo.jpg">](https://www.universal-robots.com/) &nbsp; and &nbsp;
[<img height="60" alt="FZI Research Center for Information Technology" src="doc/resources/fzi-logo_transparenz.png">](https://www.fzi.de).

<!--
    ROSIN acknowledgement from the ROSIN press kit
    @ https://github.com/rosin-project/press_kit
-->

<a href="http://rosin-project.eu">
  <img src="https://raw.githubusercontent.com/rosin-project/press_kit/master/img/rosin_ack_logo_wide.png"
       alt="rosin_logo" height="60" >
</a>

Supported by ROSIN - ROS-Industrial Quality-Assured Robot Software Components.
More information: <a href="http://rosin-project.eu">rosin-project.eu</a>

<img src="https://raw.githubusercontent.com/rosin-project/press_kit/master/img/rosin_eu_flag.jpg"
     alt="eu_flag" height="45" align="left" >

This project has received funding from the European Union’s Horizon 2020
research and innovation programme under grant agreement no. 732287.
