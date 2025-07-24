import customtkinter as AppUI
import subprocess
import paho.mqtt.client as mqtt
import time
# import gettext
import os
from PythonCommand import WorkCMD

# --- 介面設計初始化 ---

MainUI = AppUI.CTk()
MainUI.title("Smart Drink Workshop Control Panel")
MainUI.attributes('-fullscreen',True)
MainUI.bind("<Escape>", lambda e:MainUI.iconify())

title_frame = AppUI.CTkFrame(MainUI)
title_frame.pack(pady=10, padx=20, fill="both", expand=True)

motor_btn = AppUI.CTkButton(title_frame, text="Start Motor", font=("Arial", 25))
motor_btn.pack(pady=10)

MainUI.mainloop()