#!/usr/bin/env python
import os
import sys
import time
import random
import threading
from twill import get_browser, commands
#from lxml import etree
#from StringIO import StringIO
import subprocess

g_jbosses = [None, None]
g_waitTimeBeforeKillings = 25


def panic(x):
    if not x.endswith("\n"):
        x += "\n"
    sys.stderr.write("\n"+chr(27)+"[32m" + x + chr(27) + "[0m\n")
    sys.exit(1)


def mysystem(cmd):
    if 0 != os.system(cmd):
        panic("Failed to execute '%s'" % cmd)


def KillJBoss(deadBossIndex):
    print "Stopping JBoss", deadBossIndex
    g_jbosses[deadBossIndex].kill()
    print "Confirming shutdown of JBoss", deadBossIndex
    g_jbosses[deadBossIndex].wait()
    print "Confirmed."


def StartJBoss(newBossIndex):
    # Don't use os.system - see my SO question
    # http://stackoverflow.com/questions/6773456/why-is-this-code-behaving-differently-across-various-distros-unixes
    g_jbosses[newBossIndex] = \
        subprocess.Popen("./launch-node" + str(newBossIndex+1), shell=True, executable="/bin/bash")


def SpawnDistributedCluster():
    lines = filter(lambda x: "java" in x and "boss" in x and "230" in x, os.popen("COLUMNS=4000 ps aux").readlines())
    if 0 != len(lines):
        panic("Please stop your JBoss instance in the 230... multicast group")
    StartJBoss(0)
    StartJBoss(1)


class SpawnKillerThread(threading.Thread):
    def run(self):
        self.stopKilling = False
        while True:
            for _ in xrange(g_waitTimeBeforeKillings):
                time.sleep(1)
                if self.stopKilling:
                    return
            if os.path.exists("/tmp/nokill"):
                time.sleep(1)
                continue
            deadBossIndex = random.randint(0, 1)
            KillJBoss(deadBossIndex)
            StartJBoss(deadBossIndex)


def main():
    joker = SpawnKillerThread()
    try:
        SpawnDistributedCluster()
        print "Waiting for two JBoss instances to start up in cluster mode..."
        time.sleep(10)
        joker.start()
        #commands.debug('http', 1)
        #commands.debug('commands',1)
        b = get_browser()
        import socket
        myip = "http://" + socket.gethostbyname(socket.gethostname())
        b.go(myip + "/cars")
        for i in xrange(1, 500):
            b.submit('CAR-form:BtnAdd')
            commands.code(200)
            commands.formvalue("1", "newItem:j_idt24", "SuperDonkey")
            commands.formvalue("1", "newItem:j_idt26", "50")
            b.submit('newItem:enter')
            commands.code(200)
            commands.find('SuperDonkey')
            b.submit('CAR-form:BtnCommit')
            commands.code(200)
            form=b.get_form('CAR-form')
            for ctrl in form.controls:
                if ctrl.value == 'SuperDonkey':
                    removeButtonId = ":".join(ctrl.name.split(":")[:-1])
                    break
            else:
                removeButtonId = None
            if not removeButtonId:
                panic("Failed to find the remove button in the form")
            #b.submit('CAR-form:CAR-data-table:4:j_idt21')
            b.submit(removeButtonId + ':j_idt21')
            commands.code(200)
            b.submit('CAR-form:BtnCommit')
            commands.code(200)
            commands.notfind('SuperDonkey')
            time.sleep(0.25)
        #commands.reload()
    finally:
        joker.stopKilling = True
        joker.join()
        KillJBoss(0)
        KillJBoss(1)

    #commands.save_html("file")

    #html = b.get_html()
    #parser = etree.HTMLParser()
    #tree = etree.parse(StringIO(html), parser)
    #root = tree.getroot()
    #print root.xpath("//span[@id='spot-price-gold-base']")[0].text

    #for t in root.xpath("a:file/a:segments/a:segment", namespaces={'a': 'http://path.to/DTD/2003/whatever'}):
    #    f = t.getparent().getparent()
    #    s = f.get("subject")
    #    if not s:
    #        panic("Missing subject...")
    #    sizes[s] += long(t.attrib["bytes"])


if __name__ == "__main__":
    main()
