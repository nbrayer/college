.globl main
.data
name:
.ascii "noahbrayer"
.text
lui $sp 0x8000 		# initialize the stack pointer
main:
addiu $sp,$sp,-16 	# stack grows by 16 bytes
sw $ra,12($sp)		# save return address into stack
sw $s0,8($sp) 		# store $s0 so it can be used for i
sw $s1,4($sp) 		# store $s1 so it can be used for j

add $s0,$zero,$zero	# initialize $s0
add $s1,$zero,$zero	# initialize $s1
add $s2,$zero,$zero	# initialize $s2
add $t0,$zero,$zero

la  $s2,name		# Load "noahbrayer"
addi $t0,$zero,10	# $t0 = 0 + 10
addi $t1,$t0,-1	# $t2 = $t0 + (-1)
addi $t8,$zero,-1	# $t8 = 0 + (-1)

for:
slt $t2,$s0,$t1		# i < ARRAYSIZE - 1
beq $t2,$zero,exit	# If false, go to exit
add $s1,$s0,$zero	# j = i + 0

while:
slt $t5,$t8,$s1		# j>-1
beq $t5,$zero,forloop	# If false, go to forloop
add $t3,$s2,$s1		# $t3 = 0x8000 + j
lbu $t1,0($t3)		# $t4 = name[j]	
lbu $t2,1($t3)		# $t5 = name[j+1]
slt $t5,$t2,$t1		# name[j] < name[j+1]
beq $t5,$zero,forloop	# If false, go to forloop			
add $a0,$zero,$t3	# $a0 = $t3 + 0
jal swap		# Go to swap and link $ra
addi $s1,$s1,-1		# j = j + (-1)
j while			# Go to while

forloop: 
addi $s0,$s0,1		# i = i + 1
j for			# Go to for

exit:
lw $ra,12($sp)		# load saved return address into stack
lw $s0,8($sp)  		# load saved $s0
lw $s1,4($sp)  		# load saved $s1
addiu $sp,$sp,16 	# stack shrinks by 16 bytes
jr $ra 			# Go to $ra

swap:
lbu $t6,0($a0)		# loads first letter
lbu $t7,1($a0)		# loads second letter
sb $t6,1($a0)		# puts second letter where first letter was
sb $t7,0($a0)		# puts first letter where second letter was
jr $ra			#Go to $ra