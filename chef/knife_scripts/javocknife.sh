#!/bin/bash

echo "Total Arguments:" $#
echo "All Argument values:" $@

parameters=""
role=""
name=""
linuxexecutable=""
winexecutable=""

while [ -n "$1" ]; do # while loop starts

  case "$1" in
  -role)
    role=$2
    # echo "Role Selected: $role"
    shift
    ;;

  -name)
    name=$2
    # echo "Name Selected: $name"
    shift
    ;;

  -cpu)
    # echo "Loading CPU"
    # echo "-cpu $2 $3"
    parameters+="-cpu $2 $3 "
    shift
    shift
    ;;

  -cpus)
    # echo "Loading CPU"
    # echo "-cpu $2 $3"
    parameters+="-cpus $2 $3 "
    shift
    shift
    ;;

  -mem)
    # echo "Loading Memory"
    # echo "-mem $2 $3"
    parameters+="-mem $2 $3 "
    shift
    shift;;

   -mems)
    # echo "Loading Memory"
    # echo "-mem $2 $3"
    parameters+="-mems $2 $3 "
    shift
    shift;;   

  -disk)
    # echo "Loading Disk"
    # echo "-disk $2 $3"
    parameters+="-disk $2 $3 "
    shift
    shift;;

  -disks)
    # echo "Loading Disk"
    # echo "-disk $2 $3"
    parameters+="-disks $2 $3 "
    shift
    shift;;

  -netlag)
    # echo "Loading Network Latency Injector"
    # echo "-net $2 $3"
    parameters+="-netlag $2 $3 "
    shift
    shift;;

  -netlags)
    # echo "Loading Network Latency Injector"
    # echo "-net $2 $3"
    parameters+="-netlags $2 $3 "
    shift
    shift;;

  -netnoise)
    # echo "Loading Network Packet Duplicator"
    # echo "-net $2 $3"
    parameters+="-netnoise $2 $3 "
    shift
    shift;;

  -netnoises)
    # echo "Loading Network Packet Duplicator"
    # echo "-net $2 $3"
    parameters+="-netnoises $2 $3 "
    shift
    shift;;

  -netdrop)
    # echo "Loading Network Packet Dropper"
    # echo "-net $2 $3"
    parameters+="-netdrop $2 $3 "
    shift
    shift;;

  -netdrops)
    # echo "Loading Network Packet Dropper"
    # echo "-net $2 $3"
    parameters+="-netdrops $2 $3 "
    shift
    shift;;

  -netlimit)
    # echo "Loading Network Throttler"
    # echo "-net $2 $3"
    parameters+="-netlimit $2 $3 "
    shift
    shift;;

  -netlimits)
    # echo "Loading Network Throttler"
    # echo "-net $2 $3"
    parameters+="-netlimits $2 $3 "
    shift
    shift;;

  -reboot)
    # echo "Loading Machine Reboot"
    parameters+="-reboot $2"
    shift;;

  -reboots)
    # echo "Loading Machine Reboot"
    parameters+="-reboots $2"
    shift;;

  *)
    echo "Option $1 not recognized"
    ;;

  esac
  shift

done

if [[ $role == "" && $name == "" ]]; then
  echo "Please state role or name"
  exit 0

elif [[ $role == "" && $name != "" ]]; then
  linuxexecutable="knife ssh 'name:$name AND platform:*linux*' 'cd \$HOME; jdk-16.0.1/bin/java -XX:MaxRAMPercentage=100 -jar Javoc.jar $parameters'"
  winexecutable="knife winrm 'name:$name AND platform:*windows*' 'cd \$HOME; .\jdk-16.0.1\bin\java -XX:MaxRAMPercentage=100 -jar Javoc.jar $parameters' -x Administrator -P P@ssw0rd123 --winrm-shell powershell"

elif [[ $role != "" && $name == "" ]]; then
  linuxexecutable="knife ssh 'role:$role AND platform:*linux*' 'cd \$HOME; jdk-16.0.1/bin/java -XX:MaxRAMPercentage=100 -jar Javoc.jar $parameters'"
  winexecutable="knife winrm 'role:$role AND platform:*windows*' 'cd \$HOME; .\jdk-16.0.1\bin\java -XX:MaxRAMPercentage=100 -jar Javoc.jar $parameters' -x Administrator -P P@ssw0rd123 --winrm-shell powershell"

else
  linuxexecutable="knife ssh 'role:$role AND name:$name AND platform:*linux*' 'cd \$HOME; jdk-16.0.1/bin/java -XX:MaxRAMPercentage=100 -jar Javoc.jar $parameters'"
  winexecutable="knife winrm 'role:$role AND name:$name AND platform:*windows*' 'cd \$HOME; .\jdk-16.0.1\bin\java -XX:MaxRAMPercentage=100 -jar Javoc.jar $parameters' -x Administrator -P P@ssw0rd123 --winrm-shell powershell"

fi

# linuxexecutable="knife ssh 'role:$role AND platform:*linux*' 'cd \$HOME; jdk-16.0.1/bin/java -Xmx8g -jar Javoc.jar $parameters'"
# winexecutable="knife winrm 'role:$role AND platform:*windows*' 'cd \$HOME; .\jdk-16.0.1\bin\java -Xmx8g -jar Javoc.jar $parameters' -x Administrator -P P@ssw0rd123 --winrm-shell powershell"
echo $linuxexecutable
echo $winexecutable
eval "$linuxexecutable & $winexecutable"
echo "Executed"
exit 0
