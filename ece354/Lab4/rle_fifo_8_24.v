/* 
A FIFO design generated by Altera SOPC builder
Modified some parts by D.S. Kim
The original description is found under DE2_NIOS_HOST_MOUSE_VGA example (FIFO_16_256.v)
*/

//8bit FIFO. Max 256 entries are allowed 
module RLE_FIFO_8_256 (
	aclr,
	data,
	rdclk,
	rdreq,
	wrclk,
	wrreq,
	q,
	wrfull,
	rdempty);

	input	[7:0]  data;//input data to the FIFO
	input	  rdclk;    //read clk
	input	  rdreq;    //read request
	input	  wrclk;    //write clk
	input	  wrreq;    //write request
	output	[7:0]  q;   //output data from the FIFO
	output	  wrfull;   //Indicate the Buffer is full (256 entries)
	output rdempty;     //Indicate the Buffer is empty

	input	  aclr;
	
/* Modified
The original behavior : The FIFO captures 8bit input data at every clock tic, when you assert wrreq.
Modied behavior : It provides one-hot pulse (wrreq_t) when you assert wrreq for multiple clock cycle. In order words, even though wrreq is asserted for more than one clock cycle, the FIFO captures input data only one time
*/
	reg trigger;
	reg wrreq_t; /* one-hot signal of wrreq */
	wire  sub_wire0;
	wire  sub_wire2;
	
	wire [7:0] sub_wire1;
	wire  wrfull = sub_wire0;
	wire rdempty = sub_wire2;
	wire [7:0] q = sub_wire1[7:0];

	always @(posedge wrclk) begin
/* If wrreq is asserted and it never happened in previous cycle */
		if(!trigger && wrreq) begin
			wrreq_t <= 1;
			trigger <= 1;
		end
/* If wrreq is de-asserted or wrreq is already asserted in previous cycle */
		else wrreq_t <= 0;
/* Reset flag when wrreq is de-asserted */
		if(!wrreq) trigger <= 0;		
	end

////////////////////////////////////////////

	dcfifo	dcfifo_component (
				.wrclk (wrclk),
				.rdreq (rdreq),
				.aclr (aclr),
				.rdclk (rdclk),
				.wrreq (wrreq_t),
				.data (data),
				.wrfull (sub_wire0),
				.q (sub_wire1),
				.rdempty (sub_wire2)
				// synopsys translate_off
				,
				.rdfull (),
				.rdusedw (),
				.wrempty (),
				.wrusedw ()
				// synopsys translate_on
				);
	defparam
		dcfifo_component.intended_device_family = "Cyclone II",
		dcfifo_component.lpm_hint = "RAM_BLOCK_TYPE=M4K",
		dcfifo_component.lpm_numwords = 128,
		dcfifo_component.lpm_showahead = "OFF",
		dcfifo_component.lpm_type = "dcfifo",
		dcfifo_component.lpm_width = 8,
		dcfifo_component.lpm_widthu = 7,
		dcfifo_component.overflow_checking = "ON",
		dcfifo_component.rdsync_delaypipe = 4,
		dcfifo_component.underflow_checking = "ON",
		dcfifo_component.use_eab = "ON",
		dcfifo_component.wrsync_delaypipe = 4;
endmodule


/* This is 24 bit FIFO. It has similar code with 8bit FIFO except that this fifo create one-hot pulse for read request (rdreq)
*/
module RLE_FIFO_24_256 (
	aclr,
	data,
	rdclk,
	rdreq,
	wrclk,
	wrreq,
	q,
	wrfull,
	rdempty);

	input	  aclr;
	input	[23:0]  data;
	input	  rdclk;
	input	  rdreq;
	input	  wrclk;
	input	  wrreq;
	output	[23:0]  q;
	output	  wrfull;
	output rdempty;

	reg trigger;
	reg trigger_rd;
	reg wrreq_t;
	reg rdreq_t;
	wire  sub_wire0;
	wire  sub_wire2;
	
	wire [23:0] sub_wire1;
	wire  wrfull = sub_wire0;
	wire rdempty = sub_wire2;
	wire [23:0] q = sub_wire1[23:0];

	always @(posedge rdclk) begin
		if(!trigger_rd && rdreq) begin
			rdreq_t <= 1;
			trigger_rd <= 1;
		end
		else rdreq_t <= 0;

		if(!rdreq) trigger_rd <= 0;		
	end



	dcfifo	dcfifo_component (
				.wrclk (wrclk),
				.rdreq (rdreq_t),
				.aclr (aclr),
				.rdclk (rdclk),
				.wrreq (wrreq),
				.data (data),
				.wrfull (sub_wire0),
				.q (sub_wire1),
				.rdempty (sub_wire2)
				// synopsys translate_off
				,
				.rdfull (),
				.rdusedw (),
				.wrempty (),
				.wrusedw ()
				// synopsys translate_on
				);
	defparam
		dcfifo_component.intended_device_family = "Cyclone II",
		dcfifo_component.lpm_hint = "RAM_BLOCK_TYPE=M4K",
		dcfifo_component.lpm_numwords = 128,
		dcfifo_component.lpm_showahead = "OFF",
		dcfifo_component.lpm_type = "dcfifo",
		dcfifo_component.lpm_width = 24,
		dcfifo_component.lpm_widthu = 7,
		dcfifo_component.overflow_checking = "ON",
		dcfifo_component.rdsync_delaypipe = 4,
		dcfifo_component.underflow_checking = "ON",
		dcfifo_component.use_eab = "ON",
		dcfifo_component.wrsync_delaypipe = 4;


endmodule
