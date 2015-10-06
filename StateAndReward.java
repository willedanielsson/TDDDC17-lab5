public class StateAndReward {

	
	/* State discretization function for the angle controller */
	public static String getStateAngle(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */

		String state = "undefined";
		
		/*			0
		  			|
		  		A	|   B
		  			|
		  	-1.5----|-------- 1,5
		  		C	|   D
		  			|
		 			
		 */
		if(angle >-1.5 && angle <= 0.03){
			state="STATE-A";
		}else if(angle > 0.03 && angle <= 1.5){
			state="STATE-B";
		}else if(angle>-0.03 && angle <= 0.03){
			state="STATE-G";
		}else if(angle > 1.5 && angle <= 3.2){
			state="STATE-C";
		}else{
			state="STATE-D";
		}
		return state;
	}

	/* Reward function for the angle controller */
	public static double getRewardAngle(double angle, double vx, double vy) {
		double reward = 0;
		
		if(angle >-1.5 && angle <= 0.03){
			// STATE A
			reward=3;
		}else if(angle > 0.03 && angle <= 1.5){
			// STATE-B
			reward=3;
		}else if(angle > -0.03 && angle <= 0.03){
			// Goal state
			reward=10;
		}else if(angle > 1.5 && angle <= 3.2){
			// STATE D
		}else{
			//STATE-D
		}

		return reward;
	}
	
	// Changed from 14,9, 4
	public static final int ANGLE_RES = 11;
	public static final int VX_RES = 7;
	public static final int VY_RES = 7;
	public static final int VEL_BOUND=1;

	/* State discretization function for the full hover controller */
	public static String getStateHover(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		
		// From -1.5, -1.5
		String state = 
				"STATE-ANGLE-" + discretize(angle, ANGLE_RES, -0.8, 0.8) +
				"-VY-" + discretize(vy, VY_RES, -VEL_BOUND-3, VEL_BOUND);

		return state;
	}

	/* Reward function for the full hover controller */
	public static double getRewardHover(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		double angle_reward = 0;
		double y_reward = 0;
		
		int angle_state = discretize(angle, ANGLE_RES, -3, 3);
		int y_state = discretize(vy, VY_RES, -VEL_BOUND, VEL_BOUND);
		
		int angle_opt = (ANGLE_RES-1)/2;
		int y_opt = (VY_RES-1)/2;
		
		if(angle_state == angle_opt){
			angle_reward = 4*angle_opt;
		}else{
			angle_reward = (angle_opt - Math.abs(angle_state - angle_opt));
		}
		
		if(y_state==y_opt){
			y_reward= 5*y_opt;
		}else{
			y_reward = (y_opt - Math.abs(y_state - y_opt));
		}

		return angle_reward + y_reward;
	}

	// ///////////////////////////////////////////////////////////
	// discretize() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 1 and nrValues-2 is returned.
	//
	// Use discretize2() if you want a discretization method that does
	// not handle values lower than min and higher than max.
	// ///////////////////////////////////////////////////////////
	public static int discretize(double value, int nrValues, double min,
			double max) {
		if (nrValues < 2) {
			return 0;
		}

		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * (nrValues - 2)) + 1;
	}

	// ///////////////////////////////////////////////////////////
	// discretize2() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 0 and nrValues-1 is returned.
	// ///////////////////////////////////////////////////////////
	public static int discretize2(double value, int nrValues, double min,
			double max) {
		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * nrValues);
	}

}
