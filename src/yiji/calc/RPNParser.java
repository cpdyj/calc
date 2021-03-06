package yiji.calc;
import java.util.*;
public class RPNParser
{
	private HashMap<Integer,Integer> pri=new HashMap<Integer,Integer>();
	public RPNParser(){
		pri.put(Token.MUL, 3);
		pri.put(Token.DIV, 3);
		pri.put(Token.ADD, 2);
		pri.put(Token.SUB, 2);
		pri.put(Token.SPL, 1);
		pri.put(Token.INTEGER, 0);
		pri.put(Token.DOUBLE, 0);
		pri.put(Token.VAR, 0);
		pri.put(Token.LBK, 0);
		pri.put(Token.FUNC, 0);
	}
	public Stack<Token> parseToRPN(ArrayList<Token> tokenlist){
		Stack<Token> ops=new Stack<Token>();
		Stack<Token> nums=new Stack<Token>();
		for(int i=0;i<tokenlist.size();i++){
			Token tk=tokenlist.get(i);
			switch(tk.type){
				case Token.INTEGER:
				case Token.DOUBLE:
				case Token.VAR:
					nums.push(tk);
					break;
				case Token.LBK:
				case Token.FUNC:
					if(tokenlist.get(i+1).type==Token.RBK){
						//the function has no parameter.
						tk.func_has_no_param=true;
					}
					ops.push(tk);
					break;
				case Token.RBK:
					while(!ops.isEmpty()){
						Token temp=ops.pop();
						if(temp.type == Token.LBK){
							break;
						}else if(temp.type == Token.FUNC){
							nums.push(temp);
							break;
						}
						nums.push(temp);
					}
					break;
				case Token.MUL:
				case Token.DIV:
				case Token.ADD:
				case Token.SUB:
				case Token.SPL:
					while(!(ops.isEmpty()) && getpri(tk) < getpri(ops.peek())){
						nums.push(ops.pop());
					}
					ops.push(tk);
			}
		}
		while(!ops.isEmpty()){
			nums.push(ops.pop());
		}
		return nums;
	}
	private int getpri(Token tk){
		return pri.get(tk.type);
	}
}
