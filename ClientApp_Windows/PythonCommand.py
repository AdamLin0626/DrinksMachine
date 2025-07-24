import subprocess


# --- 指令對應表 ---
command_map = {
    "motor": "Test/motor.py",
    "camera": "Test/camera.py",
}

def WorkCMD(cmd):
    if cmd in command_map:
        subprocess.Popen(["python", command_map[cmd]])
    else :
        print("No any command call " + cmd)
