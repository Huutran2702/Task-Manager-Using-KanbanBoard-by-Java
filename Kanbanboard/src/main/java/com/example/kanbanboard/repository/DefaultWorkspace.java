package com.example.kanbanboard.repository;

import com.example.kanbanboard.model.Work;
import com.example.kanbanboard.model.WorkList;
import com.example.kanbanboard.model.Workspace;

import java.util.List;

public class DefaultWorkspace  {
    public static Workspace setWorkspace() {
        Work work1 = new Work(0,"Công việc 1");
        Work work2 = new Work(0,"Công việc 2");
        Work work3 = new Work(0,"Công việc 3");
        WorkList workList = new WorkList("Công việc cần làm", 0);
        WorkList workList1 = new WorkList("Công việc đang làm",1);
        WorkList workList2 = new WorkList("Công việc hoàn thành", 2);
        workList1.getItems().add(work2);
        workList2.getItems().add(work3);
        workList.getItems().add(work1);
        Workspace workspace = new Workspace("Khong gian chinh");
        workspace.getWork().add(workList);
        workspace.getWork().add(workList1);
        workspace.getWork().add(workList2);
        return workspace;
    }
    public static Workspace setWorkspace1() {
        Work work1 = new Work(0,"Công việc mới 1");
        Work work2 = new Work(0,"Công việc mới 2");
        Work work3 = new Work(0,"Công việc mới 3");
        WorkList workList = new WorkList("Công việc cần làm", 0);
        WorkList workList1 = new WorkList("Công việc đang làm",1);
        WorkList workList2 = new WorkList("Công việc hoàn thành", 2);
        workList1.getItems().add(work2);
        workList2.getItems().add(work3);
        workList.getItems().add(work1);
        Workspace workspace = new Workspace("Khong gian chinh");
        workspace.getWork().add(workList);
        workspace.getWork().add(workList1);
        workspace.getWork().add(workList2);
        return workspace;
    }
}
