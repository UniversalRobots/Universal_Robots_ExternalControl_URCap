# Simple External Control Server example
This simple server, serves the External Control with URScript provided by a file

Example of use:
```bash
python3 simple_external_control_server.py -p 50002 hello_world.script
```

To install and use the URCap external control, follow the instructions from the ROS Driver for [e-Series](https://github.com/UniversalRobots/Universal_Robots_ROS_Driver/blob/master/ur_robot_driver/doc/install_urcap_e_series.md)
or for [cb3-Series](https://github.com/UniversalRobots/Universal_Robots_ROS_Driver/blob/master/ur_robot_driver/doc/install_urcap_cb3.md).

Once you play the program on a robot, a popup with the message "Hello World" should appear.
