import customtkinter as ctk

# ----------------- 初始化 -----------------
ctk.set_appearance_mode("light")
ctk.set_default_color_theme("blue")

# ----------------- 主視窗 -----------------
app = ctk.CTk()
app.title("Smart Drink Workshop Control Panel")
app.attributes("-fullscreen", True)
app.bind("<Escape>", lambda e: app.iconify())
app.grid_rowconfigure(1, weight=1)  # 中間主區域可伸縮
app.grid_columnconfigure(0, weight=1)

# ----------------- 上方控制列  -----------------
top_frame = ctk.CTkFrame(app)

# ===== 左側：模式選擇 =====
left_panel = ctk.CTkFrame(app)

# ----------------- 中間主體區域  -----------------
main_frame = ctk.CTkFrame(app)

# ===== 右側：User Guide =====
right_panel = ctk.CTkFrame(main_frame)

# ----------------- 底部進度列 (row=2) -----------------
bottom_frame = ctk.CTkFrame(app)

# ----------------- 版面配置 -----------------
top_frame.grid(column=0, row=0, columnspan=2, sticky="nwe", padx=20, pady=20)
left_panel.grid(column=0, row=1, rowspan=2, sticky="nws", padx=20, pady=(20,20))
main_frame.grid(column=1, row=1,  sticky="news", padx=20, pady=20)
right_panel.pack( padx=20, pady=20, side="right")
bottom_frame.grid(column=1, row=2, columnspan=2, sticky="wse", padx=20, pady=20)

app.mainloop()