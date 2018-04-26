import * as WebSocket from 'ws';

export interface IMap { [s: string]:  WebSocket };

export interface IMessage {
    uri: string;
    rid: string;
    content: any;
}