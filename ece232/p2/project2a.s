.globl main
.data
Matrix:	.word 0:17
	.word 0:17
	.word 0:17
	.word 0:17
	.word 0:17
	.word 0:17
	.word 0:17
	.word 0:17
	.word 0:17
	.word 0:17
	.word 0:17
	.word 0:17
	.word 0:17
	.word 0:17
	.word 0:17
	.word 0:17
	.word 0:17
.text
addi $s0,$zero,289
add $s1,$zero,$zero
la $s2,Matrix
add $t2,$s2,$zero
for:
bge $s1,$s0,end_for
sw $s1,($t2)
addi $t2,$t2,4
addi $s1,$s1,1
j for
end_for:
.globl __start
__start: addi $s0,$zero,0	# outer loop counter
addi $s1,$zero,0		# inner loop counter
la $t0,Matrix			# Matrix, base address
addi $t6,$zero,17		# number of columns
addi $t7,$zero,17		# number of rows
sll $t1,$t6,2			# byte numbers in a row
ext_loop: addi $s1,$s0,0	# reset the inner loop counter
inn_loop: mult $s0,$t1
mflo $t2			# calculate the low byte offset
sll $t3,$s1,2			# calculate the column byte offset
add $t4,$t0,$t2			# add low offset
add $s2,$t4,$t3			# add column offset
lw $s3,0($s2)			# load this element
mult $s1,$t1			# same for the other data
mflo $t2
sll $t3,$s0,2
add $t4,$t0,$t2
add $s4,$t4,$t3
lw $s5,0($s4)
add $s6,$s3,$s5			# addition
srl $s7,$s6,1			# divided by 2
sw $s7,0($s2)			# store the result back
sw $s7,0($s4)			# store the result back
addi $s1,$s1,1			# increment the inner loop
blt $s1,$t7,inn_loop		# next inner loop
addi $s0,$s0,1			# increment the outer loop
blt $s0,$t6,ext_loop		# next outer loop
.end



