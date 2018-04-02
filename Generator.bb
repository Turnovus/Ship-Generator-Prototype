FlushKeys()
Print("Turnovus' sprite generator")
Print("Demo edition")
Print("Controls:")
Print("1 - Generate fighter")
Print("2 - Generate pod")
Print("3 - Generate bomber")
Print("4 - Generate carrier")
Print("5 - Generate destroyer")
Print("6 - Generate sword")
Print("7 - Generate scroller")
Print("8 - Generate invader")
Print("Enter - Save raster image")
Print("Press any key to get started!")
WaitKey()

If FileType("Ships") <> 2 Then
	CreateDir "Ships"
EndIf

SeedRnd(MilliSecs())
Graphics 150, 150, 16, 3
Global x
Global y

Dim ship(2,1,1)

Function generate(mode)
	Select mode
		Case 1:Restore fighter
		Case 2:Restore pod
		Case 3:Restore bomber
		Case 4:Restore carrier
		Case 5:Restore destroyer
		Case 6:Restore sword
		Case 7:Restore scroller
		Case 8:Restore invader
	End Select
	Read x
	Read y
	Read mirror
	Dim ship(2,x,y)
	For i = 0 To y - 1
		For j = 0 To x - 1
			Read a
			ship(0,j,i) = a
		Next
	Next
	
	Local r[5]
	Local g[5]
	Local b[5]
	Local pool[3]
	r[0] = 0:g[0] = 0:b[0] = 0
	r[4] = 1:g[4] = 1:b[4] = 1
	pool[0] = 0:pool[1] = 100:pool[2] = 255
	For i = 1 To 3
		While r[i] + g[i] + b[i] = 0
			r[i] = pool[Rnd(0,2)]
			g[i] = pool[Rnd(0,2)]
			b[i] = pool[Rnd(0,2)]
		Wend
	Next
	
	For i = 0 To x - 1
		For j = 0 To y - 1
			Select ship(0,i,j)
				Case 1
					a = Rnd(0,3)
					If a = 0 Then ship(1,i,j) = 1
				Case 2
					a = Rnd(0,2)
					If a = 0 Then ship(1,i,j) = 1
				Case 3
					a = Rnd(0,3)
					If a < 2 Then ship(1,i,j) = 1
					If a = 2 Then ship(1,i,j) = 2
				Case 4
					a = Rnd(0,4)
					If a < 4 Then ship(1,i,j) = 2
					If a < 2 Then ship(1,i,j) = 1
				Case 5
					a = Rnd(0,4)
					If a < 4 Then ship(1,i,j) = 2
					If a < 2 Then ship(1,i,j) = 1
					If a = 4 Then ship(1,i,j) = 3
				Case 6
					a = Rnd(0,4)
					If a <= 4 Then ship(1,i,j) = 4
					If a < 2 Then ship(1,i,j) = 1
			End Select
		Next
	Next
	
	If mirror Then
		result = CreateImage(x*2,y)
	Else
		result = CreateImage(x,y)
	EndIf
	
	SetBuffer ImageBuffer(result)
	For i = 0 To x - 1
		For j = 0 To y - 1
			Color r[ship(1,i,j)],g[ship(1,i,j)], b[ship(1,i,j)]
			Plot i,j
			If mirror Then Plot ((x*2)-1) - i,j
		Next
	Next
	Return result
End Function

Include "Databank.bb"
ClsColor 50,50,50

a = generate(1)

While Not KeyHit(1)
	
	If KeyHit(2) Then
		a = generate(1)
	ElseIf KeyHit(3) Then
		a = generate(2)
	ElseIf KeyHit(4) Then
		a = generate(3)
	ElseIf KeyHit(5) Then
		a = generate(4)
	ElseIf KeyHit(6) Then
		a = generate(5)
	ElseIf KeyHit(7) Then
		a = generate(6)
	ElseIf KeyHit(8) Then
		a = generate(7)
	ElseIf KeyHit(9) Then
		a = generate(8)
	EndIf
	
	If KeyHit(28)
		name = 1
		While FileType("Ships/" + Str$(name) + ".bmp") = 1
			name = name + 1
		Wend
		DebugLog(SaveImage(a, "Ships/" + Str$(name) + ".bmp"))
	EndIf
	
	SetBuffer FrontBuffer()
	Cls
	DrawImage(a,5,5)
	Color 0,0,0
	For c = 0 To x - 1
		For b = 0 To y - 1
			Text c * 8, 40 + b * 10, ship(1,c,b)
		Next
	Next
	
	FlushKeys
	WaitKey

Wend
End