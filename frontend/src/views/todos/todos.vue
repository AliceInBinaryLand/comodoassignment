<template>
  <v-row>
    <v-col>
      <v-data-table
          :items="todoList"
          :items-per-page="20"
          :headers="headers"
          item-key="id"
      >
        <template v-slot:top>
          <v-toolbar flat>
            <v-toolbar-title
            >
              <v-btn color="warning" text plain to="/todos">Index</v-btn>
              /<span
                class="text--disabled ml-3 subtitle-1"
            >{{ $route.name }}</span
            ></v-toolbar-title
            >
            <v-divider class="mx-4" inset vertical></v-divider>
            <v-spacer></v-spacer>
            <v-dialog v-model="openNewDialog" max-width="500px">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                    color="primary"
                    dark
                    class="mb-2"
                    v-bind="attrs"
                    v-on="on"
                    small
                    @click="openNewDialogModal(new Object())"
                >
                  New Todo
                </v-btn>
              </template>
              <v-card>
                <v-card-title>
                  <span class="text-h5">Create New Todo</span>
                </v-card-title>
                <ValidationObserver ref="customForm">
                  <v-form @submit.prevent="save">
                    <v-card-text>
                      <v-container>
                        <v-row>
                          <v-col cols="12">
                            <dm-input
                                v-model="todo.title"
                                rules="required"
                                label="Title"
                            ></dm-input>
                          </v-col>
                          <v-col cols="12">
                            <dm-text-area
                                v-model="todo.description"
                                label="Description"
                            ></dm-text-area>
                          </v-col>
                       </v-row>
                        <v-row>
                          <v-col cols="4">
                            <dm-date-input
                                v-model="todo.dueDate"
                                rules="required"
                                label="Due Date"
                            ></dm-date-input>
                          </v-col>
                          <v-col cols="4">
                            <dm-select
                                v-model="todo.priority"
                                :items="priorityList"
                                return-object
                                label="Priority"
                                rules="required"
                            ></dm-select>
                          </v-col>
                        <v-col cols="4">
                            <dm-select
                                v-model="todo.group"
                                :items="groupList"
                                item-text="name"
                                return-object
                                label="Todo Group"
                                rules="required"
                            ></dm-select>

                          </v-col>
                        </v-row>
                      </v-container>
                    </v-card-text>

                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn color="error" small text @click="closeDialog()">
                        Cancel
                      </v-btn>
                      <v-btn color="success" small type="submit"> Save</v-btn>
                    </v-card-actions>
                  </v-form>
                </ValidationObserver>
              </v-card>
            </v-dialog>
            <v-dialog v-model="deleteDialog" max-width="350px">
              <v-card>
                <v-card-title class="text-h5 text-break text-center"
                >Are you sure want to delete this item?
                </v-card-title
                >
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="error" small text @click="closeDialog"
                  >Cancel
                  </v-btn
                  >

                  <v-btn color="success" small @click="deleteItemConfirm()"
                  >OK
                  </v-btn
                  >
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-dialog v-model="doneDialog" max-width="350px">
              <v-card>
                <v-card-title class="text-h5 text-break text-center"
                >Are you sure want to mark it as done?
                </v-card-title
                >
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="error" small text @click="closeDialog"
                  >Cancel
                  </v-btn
                  >

                  <v-btn color="success" small @click="doneConfirm()"
                  >OK
                  </v-btn
                  >
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-toolbar>
        </template>

        <template v-slot:item.index="{ index }"> {{ index + 1 }}.</template>

        <template v-slot:item.title="{ item }">
          <span>
            {{item.title}}
          </span>
        </template>

        <template v-slot:item.description="{ item }">
          <span>
            {{item.description}}
          </span>
        </template>

        <template v-slot:item.dueDate="{ item }">
          <span>
            {{ item.dueDate }}
          </span>
        </template>

        <template v-slot:item.priority="{ item }">
          <span>
            {{ item.priority }}
          </span>
        </template>

        <template v-slot:item.group.name="{ item }">
          <span>
            {{ item.group.name }}
          </span>
        </template>

        <template v-slot:item.actions="{ item }">
          <v-icon color="success" small class="mr-2" @click="done(item)" title="Mark as done">
            mdi-clipboard-check
          </v-icon>
          <v-icon color="primary" small class="mr-2" @click="openNewDialogModal(item)" title="Update">
            mdi-pencil
          </v-icon>
          <v-icon color="error" small  class="mr-2" @click="deleteItem(item)" title="Delete">
            mdi-delete
          </v-icon>

        </template>
      </v-data-table>
    </v-col>
  </v-row>
</template>

<script>

import DmInput from "@/components/common/form/DmInput";
import DmTextArea from "@/components/common/form/DmTextArea";
import DmDateInput from "@/components/common/form/DmDateInput";
import DmCombo from "@/components/common/form/DmCombo";
import DmSelect from "@/components/common/form/DmSelect";
import ApiService from "@/services/api.service";

export default {
  name: "todos",
  components: {DmSelect, DmCombo, DmDateInput, DmTextArea, DmInput },
  data: () => ({
    itemName: "Todos",
    headers: [
      {value: "index", text: "#"},
      {value: "title", text: "Title"},
      {value: "description", text: "Description"},
      {value: "dueDate", text: "Due Date"},
      {value: "priority", text: "Priority"},
      {value: "group.name", text: "Group"},
      {value: "actions", text: "Actions", sortable: false},
    ],
    todoList: [],
    groupList : [],
    priorityList : [ 1, 2, 3, 4, 5 ],
    openNewDialog:false,
    todo : {id : '', creator : {}, title : '', description:'', dueDate : 0, priority : 1, group : {} },
    deleteDialog : false,
    doneDialog : false,
  }),
  methods: {
    getTodos() {
      ApiService.post("secured/todos/getTodos")
          .then(({data}) => {
            this.todoList = data;
          })
          .catch(({response}) => {
            this.$toast.setMessage("Something went wrong!", 1);
          });
    },
    save(){
      this.$refs.customForm.validate().then((success) => {
        if(success){
          ApiService.post("secured/todos/save" , this.todo)
              .then(({data}) => {
                this.$toast.setMessage("Successfully Saved", 2);
                this.closeDialog();
                this.getTodos();
              })
              .catch(({response}) => {
                this.$toast.setMessage("Something went wrong!", 1);
              });
        }
      });

    },
    openNewDialogModal(item){
      this.todo = item;
      ApiService.post("secured/groups/getGroups")
          .then(({data}) => {
            this.groupList = data;
            this.openNewDialog = true;

          })
          .catch(({response}) => {
            this.$toast.setMessage("Something went wrong!", 1);
          });
    },
    closeDialog() {
      this.openNewDialog = false;
      this.deleteDialog = false;
      this.todo = {};
      this.doneDialog = false;
    },
    deleteItem(item){
      this.deleteDialog = true;
      this.todo = item;
    },
    deleteItemConfirm(){
      ApiService.post("secured/todos/delete" , this.todo)
          .then(({data}) => {
            this.$toast.setMessage("Successfully Deleted", 2);
            this.closeDialog();
            this.getTodos();
          })
          .catch(({response}) => {
            this.$toast.setMessage("Something went wrong!", 1);
          });
    },
    done(item){
      this.todo =  item;
      this.doneDialog = true;
    },
    doneConfirm(){
      ApiService.post("secured/todos/done" , this.todo)
          .then(({data}) => {
            this.$toast.setMessage("Operation Successful", 2);
            this.closeDialog();
            this.getTodos();
          })
          .catch(({response}) => {
            this.$toast.setMessage("Something went wrong!", 1);
          });
    }

  },
  created() {
    this.getTodos();
  },

}
</script>

<style scoped>

</style>