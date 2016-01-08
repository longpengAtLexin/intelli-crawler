import java.util.Random;



/**
 * 动手方能懂啊;
 * 快速排序
 * 还得时常拿出来复习;
 * 可惜还是写错；
 * @author penglong
 *
 */
public class QuickSort 
{
	
	int arr[] ;
	
	
	public  int[] sort()
	{
		if(arr==null||arr.length<=1)
			return arr;
		else
		{
			for(int i= 0;i< arr.length-1;i++)
			{
				int key = arr[0];
				int low = 0,high = arr.length-1;
				
				while(low <high)
				{
					while( arr[low]<key)
						low++;
					while(arr[high]>key )
						high--;
					swap(low, high);
				}
			}
			return arr;
		}
		
	}
	
	// 参见维基百科;
	public void sort(int start, int end)
	{
		
		int key = arr[start];
		
		int low = start-1;
		
		int high = end;
		while(low < high)
		{
			while(arr[low] < key&&low<high)
				low ++;
			while(arr[high]>key&&low<high)
				high--;
			swap(low, high);
			
		}
		
		if (arr[low] >= arr[end])
			swap(low, end);
		else
			low++;
		sort(start, low - 1);
		sort(low + 1, end);
	}
	
	
	
	private  void swap(int i, int j)
	{
		int temp = arr[i];
		arr[i]= arr[j];
		arr[j] = temp;
	}
	
	
	public static void main(String[] args) 
	{
		
		testQuick();
		
	}
	
	public static void testSwap()
	{
		QuickSort sort = new QuickSort();
		
		sort.arr = new int[]{1,3,7};
		
		sort.swap(0, 2);
		
		System.out.println( sort.arr[0]);
		System.out.println( sort.arr[2]);
	}
	
	public static void testQuick()
	{
		
		Random random = new Random();
		
		
		int len = 10;
		
		int [] arr = new int[len];
		
		for(int i = 0;i< len ;i++)
		{
			arr[i] = random.nextInt(100);
		}
		
		printArr(arr);
		
		System.out.println();
		System.out.println("after sort ……" );
		
		QuickSort sort = new QuickSort();
		sort.arr= arr;
		
		//sort.sort();
		//printArr(sort.sort());
		
		
		sort.sort(0, arr.length-1);
		
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
