<template>
  <v-row>
    <v-col>
      <v-data-table
          :items="groups"
          :items-per-page="5"
          :headers="headers"
          item-key="id"
      >
        <template v-slot:top>
          <v-toolbar flat>
            <v-toolbar-title
            >
              <v-btn color="warning" text plain to="/users">Index</v-btn>
              /<span
                class="text--disabled ml-3 subtitle-1"
            >{{ $route.name }}</span
            ></v-toolbar-title
            >
            <v-divider class="mx-4" inset vertical></v-divider>
            <v-spacer></v-spacer>
            <v-dialog v-model="openNewGroup" max-width="500px">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                    color="primary"
                    dark
                    class="mb-2"
                    v-bind="attrs"
                    v-on="on"
                    small
                    @click="openDialog()"
                >
                  New Group
                </v-btn>
              </template>
              <v-card>
                <v-card-title>
                  <span class="text-h5">Add New Group</span>
                </v-card-title>
                <ValidationObserver ref="customForm">
                  <v-form @submit.prevent="save">
                    <v-card-text>
                      <v-container>
                        <v-row>
                          <v-col cols="12">
                            <dm-input
                                v-model="editedItem.name"
                                rules="required"
                                label="Group Name"
                            ></dm-input>
                          </v-col>
                          <v-col cols="12">
                            <dm-input
                                v-model="editedItem.description"
                                label="Description"
                            ></dm-input>
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

                  <v-btn color="success" small @click="deleteItemConfirm"
                  >OK
                  </v-btn
                  >
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-toolbar>
        </template>
        <template v-slot:item.index="{ index }"> {{ index + 1 }}.</template>

        <template v-slot:item.actions="{ item }">
          <v-icon color="primary" small class="mr-2" @click="editItem(item)">
            mdi-pencil
          </v-icon>
          <v-icon color="error" small @click="deleteItem(item)">
            mdi-delete
          </v-icon>
        </template>
      </v-data-table>
    </v-col>
  </v-row>
</template>

<script>
import ApiService from "@/services/api.service";
import DmInput from "@/components/common/form/DmInput";

export default {
  name: "groups",
  components: { DmInput },
  data: () => ({
    itemName: "Customer",
    headers: [
      {value: "index", text: "#", width: 80},
      {value: "name", text: "Group Name"},
      {value: "description", text: "Description"},
      {value: "actions", text: "Actions", sortable: false, align: "right"},
    ],
    groups: [],
    editedItem : { id :'', name: '', description : ''},
    openNewGroup: false,
    deleteDialog: false,
  }),
  methods: {
    openDialog(){
      this.openNewGroup = true;
    },
    closeDialog(){
      this.openNewGroup = false;
      this.deleteDialog = false;
      this.editedItem = {};

    },
    openDeleteDialog(){
      this.deleteDialog = true;
    },
    loadGroups() {
      ApiService.post("secured/groups/getGroups")
          .then(({data}) => {
            this.groups = data;
          })
          .catch(({response}) => {
            this.$toast.setMessage("Something went wrong!", 1);
          });
    },
    save() {
      this.$refs.customForm.validate().then((success) => {
        if (success) {
          ApiService.post("secured/groups/save", this.editedItem)
              .then(() => {
                this.$toast.setMessage("Successfully Saved", 2);
                this.closeDialog();
                this.loadGroups();
              })
              .catch(({ response }) => {
                this.$toast.setMessage(response.data, 1);
              });
        }
      });
    },
    editItem(group){
      this.editedItem = group;
      this.openDialog();
    },
    deleteItem(group){
      this.editedItem = group;
      this.openDeleteDialog();
    },
    deleteItemConfirm(){
      this.$axios
          .post("secured/groups/delete", this.editedItem)
          .then(() => {
            this.$toast.setMessage("Successfully Deleted", 2);
            this.closeDialog();
            this.loadGroups();
          })
          .catch(({ response }) => {
            this.$toast.setMessage("Group already used. Please firstly remove todos under the group", 1);
            this.closeDialog();

          });
    }
  },
  created() {
    this.loadGroups();
  }
}
</script>

<style scoped>

</style>