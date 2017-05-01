;Insertion Sort
;(c) Nightfox 2017

#MAX_ELEMENTS = 100

Dim numbers.w(#MAX_ELEMENTS)

;Count number of elements to sort
Function PopulateArray{}
  FileInput 0
  count.w = 0
  While NOT Eof(0)
    SHARED  numbers()
    numbers(count) = Edit(5)
    Print numbers(count), " "
    count = count + 1
  Wend
  Function Return count
End Function

Statement SortArray{count.w, numA.l}
  MOVE.l  d1,a0 ;Put numbers array address in register
  MOVE.w  #0,d1  ;D1 = i

outerLoop:
  ADDQ.w #1,d1  ;increment i
  CMP.w d0,d1 ;is i > count?
  BGT done  ;if so, for loop is over
  MOVE.w  d1,d2  ;D2 = j

innerLoop:
  TST.w d2  ;is j > 0
  BLE outerLoop ;if not, go back to outer loop
  MOVE.w  d2,d3 ;put j in D3
  SUBQ.w  #1,d3 ;make it j-1
  LSL.w #1,d3 ;double j-1 to allow word accesses
  MOVE.w  d2,d4 ;make a copy of j to D4
  LSL.w #1,d4 ;so that we can double it to allow word accesses
  MOVE.w  1(a0,d3),d5  ;put numbers(j-1) into D5
  MOVE.w  1(a0,d4),d6  ;put numbers(j) into D6
  CMP.w d6,d5 ;is numbers(j-1) > numbers(j)?
  BLE outerLoop ;if not, go back to outer loop
  MOVE.w d6,1(a0,d3)  ;numbers(j) -> numbers(j-1)
  MOVE.w d5,1(a0,d4)  ;numbers(j-1) -> numbers(j)
  SUBQ.w #1,d2  ;decrement j
  BRA innerLoop ;repeat inner loop

done:
  AsmExit
End Statement

WBStartup
NPrint "Insertion Sort"
NPrint "(c) Nightfox 2017"
NPrint ""

succ.l = ReadFile(0,"numbers.txt")

If succ = True
  NPrint "Unsorted list:"
  count.w = PopulateArray{}
  NPrint ""
  NPrint ""
  NPrint "The number of elements to be sorted is: ", count
  NPrint ""
  CloseFile 0
  SortArray{count, &numbers(0)}
  NPrint "Sorted list:"
  For i = 0 To (count-1)  ;print sorted list
    Print numbers(i), " "
  Next
Else
  NPrint "Error reading numbers.txt!"
  Goto exit
EndIf

exit:
NPrint ""
End





