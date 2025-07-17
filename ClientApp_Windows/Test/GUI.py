import customtkinter as ctk
import subprocess

# 設定外觀（"dark"、"light"、"system"）
ctk.set_appearance_mode("dark")
ctk.set_default_color_theme("blue")  # 可改為 green、dark-blue 等

# 建立主視窗
app = ctk.CTk()
app.title("Python 控制面板")
app.geometry("400x300")

# 定義要執行的指令
def run_motor():
    subprocess.Popen(["python", "motor.py"])

def run_camera():
    subprocess.Popen(["python", "camera.py"])

def run_shutdown():
    subprocess.Popen(["python", "shutdown.py"])

# 建立按鈕
title_label = ctk.CTkLabel(app, text="🧪 Python 控制中心", font=("Segoe UI", 20))
title_label.pack(pady=20)

motor_button = ctk.CTkButton(app, text="啟動馬達 🛞", command=run_motor)
motor_button.pack(pady=10)

camera_button = ctk.CTkButton(app, text="拍照 📸", command=run_camera)
camera_button.pack(pady=10)

shutdown_button = ctk.CTkButton(app, text="關機 ⏻", command=run_shutdown)
shutdown_button.pack(pady=10)

# 執行主迴圈
app.mainloop()
