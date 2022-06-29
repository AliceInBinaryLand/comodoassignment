<template>
  <v-menu tile v-model="menu" offset-y>
    <template v-slot:activator="{ on, attrs }">
      <v-btn icon color="primary" v-bind="attrs" v-on="on">
        <v-icon>mdi-bell</v-icon>
      </v-btn>
    </template>

    <v-card tile class="d-flex flex-column">
      <v-card-title class="py-1 text-subtitle-2">
        Messages
        <v-spacer> </v-spacer>
        <v-btn x-small text plain color="error">Clear All</v-btn>
      </v-card-title>

      <v-card-text class="flex-grow-1 overflow-auto pt-0">
        <v-list three-line dense>
          <template v-for="(message, index) in messages">
            <v-list-item class="px-0" :key="message.id">
              <v-list-item-content>
                <v-list-item-title>
                  {{ message.text }}
                </v-list-item-title>
                <v-list-item-subtitle>
                  {{ message.time }}
                </v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
            <v-divider :key="index"></v-divider>
          </template>
        </v-list>
      </v-card-text>
    </v-card>
  </v-menu>
</template>

<script>
export default {
  name: "DmNotifications",
  data: () => ({
    menu: false,
    messages: [
      { id: 1, message: "deneme message", time: "19:22" },
      { id: 2, message: "deneme message", time: "19:35" },
      { id: 3, message: "deneme message", time: "19:47" },
    ],
  }),
};
</script>

<style scoped></style>
