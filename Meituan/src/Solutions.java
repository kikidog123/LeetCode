import java.util.*;

/**
 * @author ForeverCode
 * &#064;date  2024/12/17
 */
public class Solutions {
    //2024-12-17
    /**
     * 手撕快速排序
     * 快速排序
     * 1.找到中位节点,左边<中位<右边
     * 2.继续拆分为左右两部分 直至low==high
     * 本质为使得中位左右有序,然后继续递归拆分
     * @param nums 未排序数组
     * @return 已排序数组
     */
    public int[] sortArray(int[]nums){
        QuickSorted(nums,0,nums.length-1);
        return nums;
    }
    private void QuickSorted(int[]nums,int low,int high){
        if(low<high){
            int index = getIndex(nums,low,high);
            QuickSorted(nums,low,index-1);
            QuickSorted(nums,index+1,high);
        }
    }
    private int getIndex(int[]nums,int low,int high){
        int i = low-1;//小于中位的数组的最后一个元素的索引
        int flag = nums[high];
        for(int k = low;k<high;k++){
            if(nums[k]<flag){
                i++;
                swap(nums,k,i);
            }
        }
        swap(nums,i+1,high);
        return i+1;
    }
    private void swap(int[]nums,int i,int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 堆排序
     * 堆排序 首先要建堆,从最后的根节点开始下沉,依次下沉,直至到根节点
     * 此时根节点为最大的元素.
     * 然后从根节点开始依次将根节点置于尾节点处,然后下沉.
     * @param nums 待排序数组
     * @return 排序数组
     */
    public int[]HeapSorted(int[]nums){
        int Size = nums.length;
        buildMaxHeap(nums,Size);
        for(int i = nums.length-1;i>=0;i--){
            swap(nums,0,i);
            Size--;
            MaxHeap(nums,0,Size);
        }
        return nums;
    }
    private void buildMaxHeap(int[]nums,int Size){
        for(int i = Size/2-1;i>=0;i--){
            MaxHeap(nums,i,Size);
        }
    }
    private void MaxHeap(int[]nums,int i,int Size){
        int l = i*2+1,r = i*2+2,largest = i;
        if(l<Size&&nums[l]>nums[largest]){
            largest = l;
        }
        if(r<Size&&nums[r]>nums[largest]){
            largest = r;
        }
        if(i != largest){
            swap(nums,i,largest);
            MaxHeap(nums,largest,Size);
        }
    }
    public class ListNode{
        int val;
        ListNode next;
        ListNode(){}
        ListNode(int val){this.val = val;}
        ListNode(int val,ListNode next){this.val = val;this.next = next;}
    }

    /**
     * 重排链表
     * @param head 头节点
     */
    public void reorderList(ListNode head){
        Map<Integer,ListNode>map = new HashMap<>();
        int index = 0;
        ListNode dummyNode = new ListNode();
        dummyNode.next = head;
        ListNode cur = head;
        while(cur!=null){
            map.put(index,cur);
            ListNode later = cur.next;
            cur.next = null;
            cur = later;
            index++;
        }
        ListNode pre = dummyNode;
        int start = 0,end = index-1;
        while(start<end){
            ListNode node1 = map.get(start);
            ListNode node2 = map.get(end);
            pre.next = node1;
            node1.next = node2;
            pre = node2;
            start++;
            end--;
        }
        if(start == end){
            pre.next = map.get(start);
        }
        map.get(start).next = null;
    }

    public boolean hasCycle(ListNode head){
        Set<ListNode>contain = new HashSet<>();
        while(head!=null){
            if(contain.contains(head)){
                return true;
            }
            contain.add(head);
            head = head.next;
        }
        return false;
    }


    //2024-12-18

    /**
     * 合并两个有序升序链表
     * @param list1 链表1
     * @param list2 链表2
     * @return
     */
    public ListNode mergeTwoLists(ListNode list1,ListNode list2) {
        PriorityQueue<ListNode>queue = new PriorityQueue<>((o1,o2)->{return o1.val-o2.val;});
        while(list1!=null){
            queue.offer(list1);
            list1 = list1.next;
        }
        while(list2!=null){
            queue.offer(list2);
            list2 = list2.next;
        }
        ListNode dummyNode = new ListNode();
        ListNode pre = dummyNode;
        while(!queue.isEmpty()){
            pre.next = queue.poll();
            pre = pre.next;
        }
        return dummyNode.next;
    }

    /**
     * 最长 无重复子串
     * @param s 字符串
     * @return 最长的长度
     */
    public int lengthOfLongestSubstring(String s){
        if(s.length()==0) return 0;
        int left = 0,right = 0;
        Set<Character>set = new HashSet<>();
        set.add(s.charAt(0));
        int ans = 1;
        for(right = 1;right<s.length();right++){
            char c = s.charAt(right);
            while(set.contains(c)){
                set.remove(s.charAt(left));
                left++;
            }
            set.add(c);
            ans = Math.max(ans,right - left + 1);
        }
        return ans;
    }

    /**
     * 删除所有重复的节点
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head){
        ListNode dummyNode = new ListNode();
        dummyNode.next = head;
        ListNode pre = dummyNode;
        boolean flag = false;
        while(pre.next!=null){
            flag = false;
           ListNode later = pre.next.next;
           while(later!=null&&later.val==pre.next.val){
               later = later.next;
               flag = true;
           }
           if(flag) {
               pre.next = later;
           }else{
               pre = pre.next;
           }
        }
        return dummyNode.next;
    }

    /**
     * 三数之和
     * @param nums 数组
     * @return 返回结果
     */
    public List<List<Integer>> threeSum(int[]nums){
        List<List<Integer>>ans = new LinkedList<>();
        Arrays.sort(nums);
        for(int i = 0;i<nums.length-2;i++){
            if(nums[i]>0){
                return ans;
            }
            if(i>0&&nums[i]==nums[i-1]) {
                continue;
            }
            int target = -nums[i];
            int right = nums.length-1;
            for(int left = i+1;left<nums.length&&left<right;left++){
                if(left>i+1&&nums[left]==nums[left-1]){
                    continue;
                }
                int sum = nums[left] + nums[right];
                while(left<right&&sum>target){
                    right--;
                    sum = nums[right]+nums[left];
                }
                if(left<right&&sum == target){
                    List<Integer>path = new ArrayList<>(List.of(nums[i],nums[left],nums[right]));
                    ans.add(path);
                }
            }
        }
        return ans;
    }

    /**
     * 复原IP地址
     */
    List<String>ans = new LinkedList<>();
    List<String>path = new ArrayList<>();
    public List<String>restoreIpAddresses(String s){
        dfs(s,0);
        return ans;
    }
    private void dfs(String s,int index){
        if(path.size()==4&&index==s.length()){
            StringBuilder str = new StringBuilder();
            for(String t:path){
                str.append(t);
                str.append('.');
            }
            str.deleteCharAt(str.length()-1);
            ans.add(str.toString());
        }
        for(int i = index+1;i<=s.length();i++){
            String sub = s.substring(index,i);
            if(check(sub)){
                path.add(sub);
                dfs(s,i);
                path.removeLast();
            }
        }
    }
    private boolean check(String sub){
        if(sub.length()>1&&sub.charAt(0)=='0') return false;
        int sum = 0;
        for(int i = 0;i<sub.length();i++){
            char c = sub.charAt(i);
            if(!Character.isDigit(c)){
                return false;
            }
            sum*=10;
            sum+=(c-'0');
        }
        return sum <= 255 && sum >= 0;
    }



}
