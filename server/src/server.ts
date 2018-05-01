
import * as express from 'express';
import * as http from 'http';
import * as WebSocket from 'ws';
import { IMap, IMessage } from './types';

const app = express();
    app.use((req, res, next) => {
        res.header("Access-Control-Allow-Origin", "*");
        res.header("Access-Control-Allow-Headers", "X-Requested-With");
        res.header("Access-Control-Allow-Headers", "Content-Type");
        res.header("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE, OPTIONS");
        next();
    });

const server = http.createServer(app);

const wss = new WebSocket.Server({ server });
var mappe: IMap = {};

wss.on('connection', (ws: WebSocket) => {

    ws.on('message', (msgTxt: string) => {
        const message: IMessage = JSON.parse(msgTxt);

        switch(message.uri) {
            case "details":
                mappe[message.rid] = ws;

                // forwarding to other clients
                return wss.clients
                    .forEach(client => {
                        if (client != ws) {
                            client.send(JSON.stringify({
                                uri: "broadcast:details",
                                rid: message.rid,
                                content: message.content
                            }));
                        }    
                    });

            case "broadcast:details":
                return mappe[message.rid].send(JSON.stringify({
                    uri: "details",
                    rid: message.rid,
                    content: message.content
                }))
        }

    });

    console.log("Client connected");
});

server.listen(process.env.PORT || 8000, () => {
    console.log(`Server started on port ${server.address().port}`);
});