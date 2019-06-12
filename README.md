# URcaps external control
Package for external control of a UR robot. It supports UR3, UR5 and UR10 of CB3 as well as the e-series.

## prerequisites
Inorder to enbale external control of a UR robot from a remote PC, this URcap must be installed on the UR robot and the ur\_rtde\_driver has to be installed on the the remote PC. 

## usage
* In the _Installation_ tab of Polyscope:
	* Adjust the IP adress of your robot in the _Installation_ tab of Polyscope (this step might be unnecessary in simulation). 
* On the remote PC:
	* Launch the suitable _launch_ file for UR3/UR5/UR10 and CB3/e-series.
* In the _Program_ tab of Polyscope:
	* Add this URcap to a program by selecting it from the side menu under the tab _URcap_.
	* Execute the program by pressing the _play_ button in the _Program_ tab of Polyscope.

