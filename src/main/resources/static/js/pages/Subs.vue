<template>
  <v-container>
    <v-layout justify-space-around>
      <v-list>
        <v-list-item
            v-for="item in subscribers">
          <user-link
              :user="item.subscriber"
              :size="48">
          </user-link>
          <v-btn
              @click="changeSubStat(item.subscriber.id)"
          >
            {{ item.active ? 'Dismiss' : 'Let' }}
          </v-btn>
        </v-list-item>
      </v-list>
    </v-layout>
  </v-container>
</template>

<script>
import UserLink from "../components/page/UserLink.vue";
import profile from 'api/profile'

export default {
  name: "Subs",
  components: {UserLink},

  data() {
    return {
      subscribers: []
    }
  },
  methods: {
    async changeSubStat(subId) {
      await profile.changeStatus(subId)

      const subIndex = this.subscribers.find( i => i.subscriber.id === subId)
      const sub = subIndex

      this.subscribers = [
          ...this.subscribers.slice(0, subIndex),
        {
          ...sub,
          active: !sub.active
        },
          //...this.subscribers.slice(subIndex + 1)
      ]
    }
  },
  async beforeMount() {
    const result = await profile.subscriptionList(this.$store.state.profile.id)
    this.subscribers = await result.json()
  }
}
</script>

<style scoped>

</style>