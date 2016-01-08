import java.util.Random;


/**
 * 把握本质,
 * 每次交换，把最大的放在最后;
 * @author penglong
 *
 */
public class BubbleSort 
{
	private int arr[];
	
	private void swap(int i,int j)
	{
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	public void sort( )
	{
		for(int i=0;i< arr.length;i++)
		{
			for(int j=0;j<arr.length - i-1;j++)
			{
				if(arr[j]>arr[j+1])
					swap(j+1,j);
			}
		}
	}
	
	public static void main(String[] args) 
	{
		Random random = new Random();
		
		
		int len = 15;
		
		int [] arr = new int[len];
		
		for(int i = 0;i< len ;i++)
		{
			arr[i] = random.nextInt(100);
		}
		
		printArr(arr);
		
		System.out.println();
		System.out.println("after sort ……" );
		
		BubbleSort sort = new BubbleSort();
		sort.arr= arr;
		
		sort.sort();
		
		printArr(sort.arr);
	}
	
	
	private static void printArr(int [] arr)
	{
		for(int i=0;i<arr.length;i++)
		{
			System.out.print(arr[i]);
			System.out.print(" ,");
		}
		
		
	}
}
