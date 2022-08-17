const { REST, Routes } = require('discord.js');
const { Client, GatewayIntentBits } = require('discord.js');
const { token, client_id } = require('./config.json');

const client = new Client({ intents: [GatewayIntentBits.Guilds] });

client.on('ready', () => {
	console.log(`Logged in as ${client.user.tag}!`);
});
  
client.on('interactionCreate', async interaction => {
	if (!interaction.isChatInputCommand()) return;

	if (interaction.commandName === 'gc') {
		await interaction.reply('jm!');
	}
});

client.login(token);

const commands = [
  {
    name: 'gc',
    description: 'test!',
  },
];

const rest = new REST({ version: '10' }).setToken(token);

(async () => {
  try {
    console.log('Started refreshing application (/) commands.');

    await rest.put(Routes.applicationCommands(client_id), { body: commands });

    console.log('Successfully reloaded application (/) commands.');
  } catch (error) {
    console.error(error);
  }
})();
